FROM opensuse/tumbleweed

WORKDIR /opt
RUN zypper --non-interactive in java-1_8_0-openjdk
RUN mkdir kafka ui

COPY resources/kafka ./kafka/
COPY resources/ui ./ui/
COPY resources/run.sh ./

RUN chmod -R 744 ./

EXPOSE 2181
EXPOSE 9000

ENTRYPOINT ./run.sh
