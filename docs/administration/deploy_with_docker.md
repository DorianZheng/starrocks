# Deploy StarRocks in Docker

This topic describes how to deploy StarRocks in a Docker container.

## Prerequisites

Before deploying StarRocks in Docker, make sure the following requirements are satisfied.

- **Hardware**

  You can follow these steps on relatively elementary hardware, such as a machine with 8 CPU cores and 16 GB of RAM. The CPU MUST support AVX2 instruction sets.

> **NOTE**
>
> You can run `cat /proc/cpuinfo | grep avx2` in your terminal to check if the CPU supports the AVX2 instruction sets.

- **Operating system**

  Your machine MUST be running on Linux. CentOS 7 or later is recommended.

- **Software**

  You must have Docker and MySQL client 5.5 or later versions installed on your machine.

## Create a Dockerfile

Create a Dockerfile to download and install StarRocks.

```shell
FROM centos:centos7

# Prepare the StarRocks Installer. Replace the `<specific_ver_num_of_starrocks>` below with the StarRocks version that you want to deploy, for example, `2.4.0`.
ENV StarRocks_version=<specific_ver_num_of_starrocks>

# Create the directory for deployment.
ENV StarRocks_home=/data/deploy

# Specify the download path. Replace the `<url_to_download_specific_ver_of_starrocks>` below with the download path of the specific version of StarRocks that you want to deploy.
ENV StarRocks_url=<url_to_download_specific_ver_of_starrocks>

# Install StarRocks.
RUN yum -y install wget
RUN mkdir -p $StarRocks_home
RUN wget -SO $StarRocks_home/StarRocks-${StarRocks_version}.tar.gz  $StarRocks_url
RUN cd $StarRocks_home && tar zxf StarRocks-${StarRocks_version}.tar.gz

# Install Java JDK.
RUN yum -y install java-1.8.0-openjdk-devel.x86_64
RUN rpm -ql java-1.8.0-openjdk-devel.x86_64 | grep bin$

# Create directories for FE meta and BE storage in StarRocks.
RUN mkdir -p $StarRocks_home/StarRocks-${StarRocks_version}/fe/meta
RUN mkdir -p $StarRocks_home/StarRocks-${StarRocks_version}/be/storage

# Install relevant tools.
RUN yum -y install mysql net-tools telnet

# Run the setup script.
COPY run_script.sh $StarRocks_home/run_script.sh
RUN chmod +x $StarRocks_home/run_script.sh
CMD $StarRocks_home/run_script.sh
```

> **CAUTION**
>
> - Replace the `<specific_ver_num_of_starrocks>` in the Dockerfile with the StarRocks version that you want to deploy, for example, `2.4.0`.
> - Replace the `<url_to_download_specific_ver_of_starrocks>` in the Dockerfile with the [download path](https://www.starrocks.io/download/community) of the specific version of StarRocks you expect to install.

## Create a script

Create a setup script `run_script.sh` to configure and start StarRocks.

```shell
#!/bin/bash


# Set JAVA_HOME.

JAVA_INSTALL_DIR=/usr/lib/jvm/$(rpm -aq | grep java-1.8.0-openjdk-1.8.0)
export JAVA_HOME=$JAVA_INSTALL_DIR

# Start FE.
cd $StarRocks_home/StarRocks-$StarRocks_version/fe/bin/
./start_fe.sh --daemon

# Start BE.
cd $StarRocks_home/StarRocks-$StarRocks_version/be/bin/
./start_be.sh --daemon

# Sleep until the cluster starts.
sleep 30;

# Set the BE server IP.
IP=$(ifconfig eth0 | grep 'inet' | cut -d: -f2 | awk '{print $2}')
mysql -uroot -h${IP} -P 9030 -e "alter system add backend '${IP}:9050';"

# Loop to detect the process.
while sleep 60; do
  ps aux | grep starrocks | grep -q -v grep
  PROCESS_STATUS=$?

  if [ PROCESS_STATUS -ne 0 ]; then
    echo "one of the starrocks process already exit."
    exit 1;
  fi
done
```

## Build a Docker image

Build a Docker image for StarRocks.

```shell
docker build --no-cache --progress=plain -t starrocks:1.0 .
```

## Start the Docker container

Start the Docker container. You can start it by mapping the relevant ports or with the network environment of the host machine.

- Start by mapping the ports:

```shell
docker run -p 9030:9030 -p 8030:8030 -p 8040:8040 --privileged=true -itd --name starrocks-test starrocks:1.0
```

- Start by using the network environment of the host machine:

```shell
docker run  --network host  --privileged=true -itd --name starrocks-test starrocks:1.0
```

## Log in to StarRocks

Log in to StarRocks after the Docker container is started.

```shell
mysql -uroot -h127.0.0.1 -P 9030
```

## Verify the deployment

Run the following SQLs to check if StarRocks is successfully deployed in the Docker container.

```sql
CREATE DATABASE TEST;

USE TEST;

CREATE TABLE `sr_on_mac` (
 `c0` int(11) NULL COMMENT "",
 `c1` date NULL COMMENT "",
 `c2` datetime NULL COMMENT "",
 `c3` varchar(65533) NULL COMMENT ""
) ENGINE=OLAP 
DUPLICATE KEY(`c0`)
PARTITION BY RANGE (c1) (
  START ("2022-02-01") END ("2022-02-10") EVERY (INTERVAL 1 DAY)
)
DISTRIBUTED BY HASH(`c0`) BUCKETS 1 
PROPERTIES (
"replication_num" = "1",
"in_memory" = "false",
"storage_format" = "DEFAULT"
);


insert into sr_on_mac values (1, '2022-02-01', '2022-02-01 10:47:57', '111');
insert into sr_on_mac values (2, '2022-02-02', '2022-02-02 10:47:57', '222');
insert into sr_on_mac values (3, '2022-02-03', '2022-02-03 10:47:57', '333');


select * from sr_on_mac where c1 >= '2022-02-02';
```

If no error is returned, you have successfully deployed StarRocks in Docker.
