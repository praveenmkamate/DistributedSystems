FROM openjdk:17-jdk
ENV PORT=8100
ENV SQL=mssql
ENV EUREKA=eureka
ENV eurekaURL="http://$EUREKA:8761/eureka/"
ENV dataSourceURL="jdbc:sqlserver://$SQL:1433;trustServerCertificate=true;integratedSecurity=false;encrypt=false"
ENV RABBITMQ=rabbitmq

COPY target/customer-1.0.0.jar /customer-1.0.0.jar
CMD /usr/bin/java -jar -Dserver.port=$PORT \
    -Dspring.datasource.url=$dataSourceURL \
    -Deureka.client.serviceUrl.defaultZone=$eurekaURL \
    -Dspring.rabbitmq.host=$RABBITMQ \
    /customer-1.0.0.jar