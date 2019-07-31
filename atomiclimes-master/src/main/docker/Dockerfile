FROM opensuse/tumbleweed

WORKDIR /opt

RUN mkdir config

COPY resources/* ./

RUN zypper --non-interactive in java-1_8_0-openjdk iputils emacs-nox curl nmap

ADD /maven/${project.name}-${project.version}.jar app.jar

ENTRYPOINT ./run.sh