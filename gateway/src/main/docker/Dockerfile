FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD gateway.jar gateway.jar
RUN sh -c 'touch /gateway.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /gateway.jar
