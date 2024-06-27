// Copyright 2021-present StarRocks, Inc. All rights reserved.

package com.starrocks.connector.delta;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.starrocks.catalog.Database;
import com.starrocks.catalog.DeltaLakeTable;
import com.starrocks.common.profile.Timer;
import com.starrocks.common.profile.Tracers;
import com.starrocks.connector.ConnectorTableId;
import com.starrocks.connector.HdfsEnvironment;
import com.starrocks.connector.exception.StarRocksConnectorException;
import com.starrocks.connector.metastore.IMetastore;
import com.starrocks.connector.metastore.MetastoreTable;
import io.delta.kernel.Scan;
import io.delta.kernel.ScanBuilder;
import io.delta.kernel.data.FilteredColumnarBatch;
import io.delta.kernel.data.Row;
import io.delta.kernel.engine.Engine;
import io.delta.kernel.internal.InternalScanFileUtils;
import io.delta.kernel.utils.CloseableIterator;
import io.unitycatalog.client.ApiClient;
import io.unitycatalog.client.ApiException;
import io.unitycatalog.client.api.SchemasApi;
import io.unitycatalog.client.api.TablesApi;
import io.unitycatalog.client.model.SchemaInfo;
import io.unitycatalog.client.model.TableInfo;
import io.unitycatalog.client.model.TableType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.starrocks.common.profile.Tracers.Module.EXTERNAL;
import static com.starrocks.connector.PartitionUtil.toHivePartitionName;

public class UnityCatalogMetastore implements IMetastore {
    private static final Logger LOG = LogManager.getLogger(UnityCatalogMetastore.class);
    public static final String UNITY_CATALOG_HOST = "uc.host";
    public static final String UNITY_CATALOG_TOKEN = "uc.token";
    public static final String UNITY_CATALOG_NAME = "uc.catalog.name";

    private final String catalogName;
    private final String databricksCatalogName;

    private final ApiClient apiClient;
    private final SchemasApi schemasApi;
    private final TablesApi tablesApi;
    private final HdfsEnvironment hdfsEnvironment;

    public UnityCatalogMetastore(String catalogName, String databricksCatalogName,
                                    ApiClient apiClient,
                                    HdfsEnvironment hdfsEnvironment) {
        this.catalogName = catalogName;
        this.databricksCatalogName = databricksCatalogName;
        this.apiClient = apiClient;
        schemasApi = new SchemasApi(apiClient);
        tablesApi = new TablesApi(apiClient);
        this.hdfsEnvironment = hdfsEnvironment;
    }

    public List<String> getAllDatabaseNames() {
        try (Timer ignored = Tracers.watchScope(EXTERNAL, "UNITY.getAllDatabases")) {
            List<String> dbNames = Lists.newArrayList();
            try {
                dbNames = Streams.stream(
                                Objects.requireNonNull(schemasApi.listSchemas(databricksCatalogName, 100, null).getSchemas()).iterator()).
                        map(SchemaInfo::getName).collect(Collectors.toList());
            } catch (NullPointerException e) {
                LOG.warn("Null pointer exception when get all databases from {} catalog", databricksCatalogName);
            } catch (ApiException e) {
                LOG.error("Catalog {} get all databases failed", databricksCatalogName, e);
            }
            return dbNames;
        }
    }

    public List<String> getAllTableNames(String dbName) {
        try (Timer ignored = Tracers.watchScope(EXTERNAL, "UNITY.getAllTables")) {
            List<String> tableNames = Lists.newArrayList();
            try {
                tableNames = Streams.stream(tablesApi.listTables(databricksCatalogName, dbName, 100, null).getTables()).
                        filter(tableInfo -> tableInfo.getTableType().equals(TableType.MANAGED)).
                        map(TableInfo::getName).collect(Collectors.toList());
            } catch (NullPointerException e) {
                // empty database will throw null pointer exception, catch here and return empty list
                LOG.warn("Null pointer exception when get all tables from {}.{}", databricksCatalogName, dbName);
            } catch (ApiException e) {
                LOG.error("Database {}.{} get all tables failed", databricksCatalogName, dbName, e);
            }
            return tableNames;
        }
    }

    public Database getDb(String dbName) {
        try (Timer ignored = Tracers.watchScope(EXTERNAL, "UNITY.getDatabase")) {
            SchemaInfo schemaInfo = schemasApi.getSchema(databricksCatalogName + "." + dbName);
            if (schemaInfo == null) {
                throw new StarRocksConnectorException("Databricks database [%s] doesn't exist", dbName);
            }
            return new Database(ConnectorTableId.CONNECTOR_ID_GENERATOR.getNextId().asInt(), schemaInfo.getName(),
                    "schemaInfo.getStorageLocation()");
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MetastoreTable getMetastoreTable(String dbName, String tableName) {
        try (Timer ignored = Tracers.watchScope(EXTERNAL, "UNITY.getMetastoreTable")) {
            String fullName = Joiner.on(".").join(databricksCatalogName, dbName, tableName);
            TableInfo tableInfo = tablesApi.getTable(fullName);
            if (tableInfo == null) {
                return null;
            }
            if (!tableInfo.getTableType().equals(TableType.MANAGED)) {
                return null;
            }
            String path = tableInfo.getStorageLocation();
            long createTime = tableInfo.getCreatedAt();
            return new MetastoreTable(dbName, tableName, path, createTime);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public DeltaLakeTable getTable(String dbName, String tblName) {
        try {
            String fullName = Joiner.on(".").join(databricksCatalogName, dbName, tblName);
            TableInfo tableInfo = tablesApi.getTable(fullName);
            if (tableInfo == null) {
                return null;
            }
            if (!tableInfo.getTableType().equals(TableType.MANAGED)) {
                return null;
            }
            String path = tableInfo.getStorageLocation();
            long createTime = tableInfo.getCreatedAt();
            return DeltaUtils.convertDeltaToSRTable(catalogName, dbName, tblName, path,
                    hdfsEnvironment.getConfiguration(), createTime);
        } catch (Exception e) {
            LOG.error("Failed to get table {}.{}.{}", catalogName, dbName, tblName, e);
            return null;
        }
    }

    public boolean tableExists(String dbName, String tblName) {
        String fullName = Joiner.on(".").join(databricksCatalogName, dbName, tblName);
        TableInfo tableInfo = null;
        try {
            tableInfo = tablesApi.getTable(fullName);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return tableInfo != null;
    }

    public List<String> getPartitionKeys(String dbName, String tblName) {
        DeltaLakeTable deltaLakeTable = getTable(dbName, tblName);
        if (deltaLakeTable == null) {
            LOG.error("Table {}.{}.{} doesn't exist", catalogName, dbName, tblName);
            return Lists.newArrayList();
        }

        List<String> partitionKeys = Lists.newArrayList();
        Engine deltaEngine = deltaLakeTable.getDeltaEngine();
        List<String> partitionColumnNames = deltaLakeTable.getPartitionColumnNames();

        ScanBuilder scanBuilder = deltaLakeTable.getDeltaSnapshot().getScanBuilder(deltaEngine);
        Scan scan = scanBuilder.build();
        try (CloseableIterator<FilteredColumnarBatch> scanFilesAsBatches = scan.getScanFiles(deltaEngine)) {
            while (scanFilesAsBatches.hasNext()) {
                FilteredColumnarBatch scanFileBatch = scanFilesAsBatches.next();

                try (CloseableIterator<Row> scanFileRows = scanFileBatch.getRows()) {
                    while (scanFileRows.hasNext()) {
                        Row scanFileRow = scanFileRows.next();
                        Map<String, String> partitionValueMap = InternalScanFileUtils.getPartitionValues(scanFileRow);
                        List<String> partitionValues =
                                partitionColumnNames.stream().map(partitionValueMap::get).collect(
                                        Collectors.toList());
                        String partitionName = toHivePartitionName(partitionColumnNames, partitionValues);
                        partitionKeys.add(partitionName);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to get partition keys for table {}.{}.{}", catalogName, dbName, tblName, e);
            throw new StarRocksConnectorException(String.format("Failed to get partition keys for table %s.%s.%s",
                    catalogName, dbName, tblName), e);
        }

        return partitionKeys;
    }
}
