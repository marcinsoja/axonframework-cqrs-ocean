FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD eureka.jar eureka.jar
RUN sh -c 'touch /eureka.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /eureka.jar
