FROM openjdk:8-jre-alpine
ENV SERVER_HOST=localhost
COPY target/dodgydrivers-1.0.jar /dodgydrivers-1.0.jar
CMD /usr/bin/java -jar /dodgydrivers-1.0.jar $SERVER_HOST
