FROM opensuse/tumbleweed

ENV KAFKA_HOME /opt/kafka

WORKDIR /opt
RUN zypper --non-interactive in java-1_8_0-openjdk iputils emacs-nox curl nmap
RUN mkdir kafka ui

COPY resources/kafka ./kafka/
COPY resources/ui ./ui/
COPY resources/run_kafka_config.sh ./
COPY resources/run.sh ./

RUN chmod -R 744 ./

EXPOSE 2181
EXPOSE 9000
EXPOSE 9092

ENTRYPOINT ./run.sh
