/*
 * Unity Catalog API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.unitycatalog.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;


/**
 * TableInfo
 */
@JsonPropertyOrder({
  TableInfo.JSON_PROPERTY_NAME,
  TableInfo.JSON_PROPERTY_CATALOG_NAME,
  TableInfo.JSON_PROPERTY_SCHEMA_NAME,
  TableInfo.JSON_PROPERTY_TABLE_TYPE,
  TableInfo.JSON_PROPERTY_DATA_SOURCE_FORMAT,
  TableInfo.JSON_PROPERTY_COLUMNS,
  TableInfo.JSON_PROPERTY_STORAGE_LOCATION,
  TableInfo.JSON_PROPERTY_COMMENT,
  TableInfo.JSON_PROPERTY_PROPERTIES,
  TableInfo.JSON_PROPERTY_CREATED_AT,
  TableInfo.JSON_PROPERTY_UPDATED_AT,
  TableInfo.JSON_PROPERTY_TABLE_ID
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.5.0")
public class TableInfo {
  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_CATALOG_NAME = "catalog_name";
  private String catalogName;

  public static final String JSON_PROPERTY_SCHEMA_NAME = "schema_name";
  private String schemaName;

  public static final String JSON_PROPERTY_TABLE_TYPE = "table_type";
  private TableType tableType;

  public static final String JSON_PROPERTY_DATA_SOURCE_FORMAT = "data_source_format";
  private DataSourceFormat dataSourceFormat;

  public static final String JSON_PROPERTY_COLUMNS = "columns";
  private List<ColumnInfo> columns = new ArrayList<>();

  public static final String JSON_PROPERTY_STORAGE_LOCATION = "storage_location";
  private String storageLocation;

  public static final String JSON_PROPERTY_COMMENT = "comment";
  private String comment;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  private Map<String, String> properties = new HashMap<>();

  public static final String JSON_PROPERTY_CREATED_AT = "created_at";
  private Long createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
  private Long updatedAt;

  public static final String JSON_PROPERTY_TABLE_ID = "table_id";
  private String tableId;

  public TableInfo() { 
  }

  public TableInfo name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of table, relative to parent schema.
   * @return name
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(String name) {
    this.name = name;
  }


  public TableInfo catalogName(String catalogName) {
    this.catalogName = catalogName;
    return this;
  }

   /**
   * Name of parent catalog.
   * @return catalogName
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CATALOG_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCatalogName() {
    return catalogName;
  }


  @JsonProperty(JSON_PROPERTY_CATALOG_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }


  public TableInfo schemaName(String schemaName) {
    this.schemaName = schemaName;
    return this;
  }

   /**
   * Name of parent schema relative to its parent catalog.
   * @return schemaName
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SCHEMA_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSchemaName() {
    return schemaName;
  }


  @JsonProperty(JSON_PROPERTY_SCHEMA_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }


  public TableInfo tableType(TableType tableType) {
    this.tableType = tableType;
    return this;
  }

   /**
   * Get tableType
   * @return tableType
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TABLE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TableType getTableType() {
    return tableType;
  }


  @JsonProperty(JSON_PROPERTY_TABLE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTableType(TableType tableType) {
    this.tableType = tableType;
  }


  public TableInfo dataSourceFormat(DataSourceFormat dataSourceFormat) {
    this.dataSourceFormat = dataSourceFormat;
    return this;
  }

   /**
   * Get dataSourceFormat
   * @return dataSourceFormat
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DATA_SOURCE_FORMAT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public DataSourceFormat getDataSourceFormat() {
    return dataSourceFormat;
  }


  @JsonProperty(JSON_PROPERTY_DATA_SOURCE_FORMAT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDataSourceFormat(DataSourceFormat dataSourceFormat) {
    this.dataSourceFormat = dataSourceFormat;
  }


  public TableInfo columns(List<ColumnInfo> columns) {
    this.columns = columns;
    return this;
  }

  public TableInfo addColumnsItem(ColumnInfo columnsItem) {
    if (this.columns == null) {
      this.columns = new ArrayList<>();
    }
    this.columns.add(columnsItem);
    return this;
  }

   /**
   * The array of __ColumnInfo__ definitions of the table&#39;s columns.
   * @return columns
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ColumnInfo> getColumns() {
    return columns;
  }


  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setColumns(List<ColumnInfo> columns) {
    this.columns = columns;
  }


  public TableInfo storageLocation(String storageLocation) {
    this.storageLocation = storageLocation;
    return this;
  }

   /**
   * Storage root URL for table (for **MANAGED**, **EXTERNAL** tables)
   * @return storageLocation
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STORAGE_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getStorageLocation() {
    return storageLocation;
  }


  @JsonProperty(JSON_PROPERTY_STORAGE_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStorageLocation(String storageLocation) {
    this.storageLocation = storageLocation;
  }


  public TableInfo comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * User-provided free-form text description.
   * @return comment
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COMMENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getComment() {
    return comment;
  }


  @JsonProperty(JSON_PROPERTY_COMMENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setComment(String comment) {
    this.comment = comment;
  }


  public TableInfo properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public TableInfo putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

   /**
   * A map of key-value properties attached to the securable.
   * @return properties
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, String> getProperties() {
    return properties;
  }


  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }


  public TableInfo createdAt(Long createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Time at which this table was created, in epoch milliseconds.
   * @return createdAt
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CREATED_AT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getCreatedAt() {
    return createdAt;
  }


  @JsonProperty(JSON_PROPERTY_CREATED_AT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }


  public TableInfo updatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Time at which this table was last modified, in epoch milliseconds.
   * @return updatedAt
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_UPDATED_AT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getUpdatedAt() {
    return updatedAt;
  }


  @JsonProperty(JSON_PROPERTY_UPDATED_AT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
  }


  public TableInfo tableId(String tableId) {
    this.tableId = tableId;
    return this;
  }

   /**
   * Unique identifier for the table.
   * @return tableId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TABLE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTableId() {
    return tableId;
  }


  @JsonProperty(JSON_PROPERTY_TABLE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTableId(String tableId) {
    this.tableId = tableId;
  }


  /**
   * Return true if this TableInfo object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TableInfo tableInfo = (TableInfo) o;
    return Objects.equals(this.name, tableInfo.name) &&
        Objects.equals(this.catalogName, tableInfo.catalogName) &&
        Objects.equals(this.schemaName, tableInfo.schemaName) &&
        Objects.equals(this.tableType, tableInfo.tableType) &&
        Objects.equals(this.dataSourceFormat, tableInfo.dataSourceFormat) &&
        Objects.equals(this.columns, tableInfo.columns) &&
        Objects.equals(this.storageLocation, tableInfo.storageLocation) &&
        Objects.equals(this.comment, tableInfo.comment) &&
        Objects.equals(this.properties, tableInfo.properties) &&
        Objects.equals(this.createdAt, tableInfo.createdAt) &&
        Objects.equals(this.updatedAt, tableInfo.updatedAt) &&
        Objects.equals(this.tableId, tableInfo.tableId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, catalogName, schemaName, tableType, dataSourceFormat, columns, storageLocation, comment, properties, createdAt, updatedAt, tableId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TableInfo {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    catalogName: ").append(toIndentedString(catalogName)).append("\n");
    sb.append("    schemaName: ").append(toIndentedString(schemaName)).append("\n");
    sb.append("    tableType: ").append(toIndentedString(tableType)).append("\n");
    sb.append("    dataSourceFormat: ").append(toIndentedString(dataSourceFormat)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    storageLocation: ").append(toIndentedString(storageLocation)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    tableId: ").append(toIndentedString(tableId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `name` to the URL query string
    if (getName() != null) {
      joiner.add(String.format("%sname%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getName()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `catalog_name` to the URL query string
    if (getCatalogName() != null) {
      joiner.add(String.format("%scatalog_name%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getCatalogName()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `schema_name` to the URL query string
    if (getSchemaName() != null) {
      joiner.add(String.format("%sschema_name%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getSchemaName()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `table_type` to the URL query string
    if (getTableType() != null) {
      joiner.add(String.format("%stable_type%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getTableType()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `data_source_format` to the URL query string
    if (getDataSourceFormat() != null) {
      joiner.add(String.format("%sdata_source_format%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getDataSourceFormat()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `columns` to the URL query string
    if (getColumns() != null) {
      for (int i = 0; i < getColumns().size(); i++) {
        if (getColumns().get(i) != null) {
          joiner.add(getColumns().get(i).toUrlQueryString(String.format("%scolumns%s%s", prefix, suffix,
          "".equals(suffix) ? "" : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    // add `storage_location` to the URL query string
    if (getStorageLocation() != null) {
      joiner.add(String.format("%sstorage_location%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getStorageLocation()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `comment` to the URL query string
    if (getComment() != null) {
      joiner.add(String.format("%scomment%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getComment()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `properties` to the URL query string
    if (getProperties() != null) {
      for (String _key : getProperties().keySet()) {
        joiner.add(String.format("%sproperties%s%s=%s", prefix, suffix,
            "".equals(suffix) ? "" : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
            getProperties().get(_key), URLEncoder.encode(String.valueOf(getProperties().get(_key)), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
      }
    }

    // add `created_at` to the URL query string
    if (getCreatedAt() != null) {
      joiner.add(String.format("%screated_at%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getCreatedAt()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `updated_at` to the URL query string
    if (getUpdatedAt() != null) {
      joiner.add(String.format("%supdated_at%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getUpdatedAt()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `table_id` to the URL query string
    if (getTableId() != null) {
      joiner.add(String.format("%stable_id%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getTableId()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    return joiner.toString();
  }
}
