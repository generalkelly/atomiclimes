FROM nexus.atomiclimes.io/atomiclimes-baseimage

ENV KAFKA_HOME /opt/kafka

RUN mkdir kafka ui

COPY src/main/docker/resources/kafka ./kafka/
COPY src/main/docker/resources/ui ./ui/
COPY src/main/docker/resources/run_kafka_config.sh ./
COPY src/main/docker/resources/run.sh ./

RUN chmod -R 744 ./

EXPOSE 2181
EXPOSE 9000
EXPOSE 9092
