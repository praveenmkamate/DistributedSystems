# Avengers

## Summary

Avengers Food Delivery is a distributed application made of 3 major 
services namely customer, restaurant and delivery service. These services are deployed in a distributed 
environment along with Spring API gateway, Eureka Service discovery, MSSQL Server and RabbitMQ.

This application is used by customers to view menus of different restaurants and place orders. It is also used by 
restaurant users for viewing and updating the menu items and also to update the customer order status. It is also 
used by delivery agents to accept orders and update the delivery status of customer orders.

## Requirements

1. Maven latest version
2. Java JDK/JRE 17
3. Docker desktop

## How to run

Clone the project
```
git clone https://gitlab.com/comp30220/2022/avengers.git
cd avengers
```

Build the project - open command prompt in project folder

```
mvn clean install
```
Ensure docker is running and execute the below command

```
docker compose up --build
```

To access the application, open browser window and type

```
http://localhost:3000
```

## Report 

[Project Report](https://gitlab.com/comp30220/2022/avengers/-/blob/main/Avengers_Report.pdf)

## Video

[Demo Video](https://gitlab.com/comp30220/2022/avengers/-/blob/main/Demo_video_avengers.mp4)