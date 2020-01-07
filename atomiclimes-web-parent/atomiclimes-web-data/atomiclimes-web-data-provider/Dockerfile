FROM nexus.atomiclimes.io/atomiclimes-baseimage

ARG JAR_FILE

WORKDIR /opt

COPY src/main/docker/resources/* ./

RUN zypper --non-interactive in java-1_8_0-openjdk

ADD /target/${JAR_FILE} app.jar

ENTRYPOINT ./run.sh