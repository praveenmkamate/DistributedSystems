FROM openjdk:17-jdk
ENV PORT=8101
ENV SQL=mssql
ENV EUREKA=eureka
ENV eurekaURL="http://$EUREKA:8761/eureka/"
ENV dataSourceURL="jdbc:sqlserver://$SQL:1433;trustServerCertificate=true;integratedSecurity=false;encrypt=false"
ENV RABBITMQ=rabbitmq

COPY target/restaurant-1.0.0.jar /restaurant-1.0.0.jar
CMD /usr/bin/java -jar -Dserver.port=$PORT \
    -Dspring.datasource.url=$dataSourceURL \
    -Deureka.client.serviceUrl.defaultZone=$eurekaURL \
    -Dspring.rabbitmq.host=$RABBITMQ \
    /restaurant-1.0.0.jar
