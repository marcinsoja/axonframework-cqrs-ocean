FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD command.jar command.jar
RUN sh -c 'touch /command.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /command.jar
