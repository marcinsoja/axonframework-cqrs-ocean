FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD query.jar query.jar
RUN sh -c 'touch /query.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /query.jar
