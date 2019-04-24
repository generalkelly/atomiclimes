FROM opensuse/tumbleweed

WORKDIR /opt
RUN zypper --non-interactive in openldap

RUN mkdir apache_directory

COPY resources/apache_directory ./apache_directory/
COPY resources/run.sh ./

RUN chmod -R 744 ./

EXPOSE 10389

#ENTRYPOINT ./run.sh
#ENTRYPOINT /bin/bash
ENTRYPOINT openldap