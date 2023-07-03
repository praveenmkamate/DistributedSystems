FROM openjdk:17-jdk
ENV PORT=8761
COPY target/service-discovery-1.0.0.jar /service-discovery-1.0.0.jar
EXPOSE $PORT
CMD /usr/bin/java -jar -Dserver.port=$PORT /service-discovery-1.0.0.jar