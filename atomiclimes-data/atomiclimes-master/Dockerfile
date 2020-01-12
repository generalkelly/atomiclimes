FROM nexus.atomiclimes.io/atomiclimes-baseimage

ARG JAR_FILE

RUN mkdir config

COPY src/main/docker/resources/* ./

ADD /target/${JAR_FILE} app.jar