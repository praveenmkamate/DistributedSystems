FROM openjdk:8-jre-alpine
ENV SERVER_HOST=localhost
COPY target/broker-1.0.jar /broker-1.0.jar
CMD /usr/bin/java -jar /broker-1.0.jar $SERVER_HOST
