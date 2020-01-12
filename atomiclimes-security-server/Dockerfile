FROM nexus.atomiclimes.io/atomiclimes-baseimage

ARG JAR_FILE

COPY src/main/docker/resources/* ./

ADD /target/${JAR_FILE} app.jar