#!/bin/bash
#STARTING ZOOKEEPER
echo "Configuring environment for kafka"
./run_kafka_config.sh
echo "Starting Zookeeper..."
./kafka/bin/zookeeper-server-start.sh ./kafka/config/zookeeper.properties &
echo "Starting Kafka..."
./kafka/bin/kafka-server-start.sh ./kafka/config/server.properties &
echo "Starting Kafka UI..."
./ui/bin/kafka-manager -Dconfig.file=./ui/conf/application.conf