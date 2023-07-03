FROM openjdk:17-jdk
ENV PORT=8050
ENV EUREKA=eureka
ENV eurekaURL="http://$EUREKA:8761/eureka/"

COPY target/gateway-1.0.0.jar /gateway-1.0.0.jar
CMD /usr/bin/java -jar -Dserver.port=$PORT \
    -Deureka.client.serviceUrl.defaultZone=$eurekaURL \
    /gateway-1.0.0.jar