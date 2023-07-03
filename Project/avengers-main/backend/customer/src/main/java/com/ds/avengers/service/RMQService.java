package com.ds.avengers.service;

import com.ds.avengers.dto.CustomerOrder;
import com.ds.avengers.rmq.RMQConnector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ds.avengers.Constants.*;


/**
 *
 * This class used to create RMQ connection on start of service,
 * Shall create a common channel & connection.
 *
 **/
@Service
public class RMQService {

    public static Connection rmqConnection;
    public static Channel rmqChannel;

    public static RMQConnector rmqConnector = new RMQConnector();

    @Value("${spring.rabbitmq.host}") private String rabbitmqHostName;

    public RMQService(@Value("${spring.rabbitmq.host}") String rabbitmqHostName) {
        createRMQConnection(rabbitmqHostName);
    }

    public RMQService() {
    }

    public static void createRMQConnection(String rabbitmqHostName) {
        Channel channel = getRMQChannel(true, rabbitmqHostName);
        try {
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(cusToResQueueName, true, false, false, null);
            channel.queueBind(cusToResQueueName, exchangeName, cusToResRoutingKey);
            channel.queueDeclare(deliveryToCustomerQueueName, true, false, false, null);
            channel.queueBind(deliveryToCustomerQueueName, exchangeName, deliveryToCustomerRoutingKey);
            channel.queueDeclare(cusToUIQueueName, true, false, false, null);
            channel.queueBind(cusToUIQueueName, exchangeName, cusToUIRoutingKey);
            deliveryUpdatesHandler(rabbitmqHostName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deliveryUpdatesHandler(String rabbitmqHostName) throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Deserializing JSON to Object");
            CustomerOrder co = mapper.readValue(message, CustomerOrder.class);
            System.out.println("Received Delivery Update Message'" + co + "'");
            message = mapper.writeValueAsString(co);
            getRMQChannel(false, rabbitmqHostName).basicPublish(exchangeName, cusToUIRoutingKey, null, message.getBytes(StandardCharsets.UTF_8));
        };
        getRMQChannel(true, rabbitmqHostName).basicConsume(deliveryToCustomerQueueName, true, deliverCallback, consumerTag -> { });
    }

    public static Channel getRMQChannel(boolean isNew, String rabbitmqHostName) {
        Channel channel;
        while (true) {
            try {
                if (rmqConnection == null || !rmqConnection.isOpen()) {
                    rmqConnection = rmqConnector.getRMQConnection("CustomerService", rabbitmqHostName);
                    rmqChannel = rmqConnection.createChannel();
                    channel = rmqChannel;
                } else {
                    if (isNew) {
                        channel = rmqConnection.createChannel();
                    } else {
                        channel = rmqChannel;
                    }
                }
                break;
            } catch (IOException e) {
                System.out.println("Error in creating a rabbitmq connection or channel" + e);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return channel;
    }

    public void sendUpdateToRestaurantService(CustomerOrder customerOrder) {
        try {
            //Creating the ObjectMapper object
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString
            String message = mapper.writeValueAsString(customerOrder);
            getRMQChannel(false, rabbitmqHostName).basicPublish(exchangeName, cusToResRoutingKey, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Failed to send update to Restaurant service " + e);
        }
    }
}
