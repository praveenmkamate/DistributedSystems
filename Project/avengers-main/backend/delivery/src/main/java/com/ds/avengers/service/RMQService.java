package com.ds.avengers.service;

import com.ds.avengers.dto.CustomerOrder;
import com.ds.avengers.rmq.RMQConnector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ds.avengers.Constants.*;

@Service
public class RMQService {
    public static Connection rmqConnection;
    public static Channel rmqChannel;

    @Autowired
    private DeliveryService deliveryService;

    public static RMQConnector rmqConnector = new RMQConnector();

    public RMQService(@Value("${spring.rabbitmq.host}") String rabbitmqHostName) {
        createRabbitMQConnection(rabbitmqHostName);
    }

    private void createRabbitMQConnection(String rabbitmqHostName) {

        Channel channel = getRMQChannel(true,rabbitmqHostName);

        try {
            channel.exchangeDeclare(restaurantToDeliveryExchangeName, "direct", true);
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(restaurantToDeliveryQueueName, true, false, false, null);
            channel.queueBind(restaurantToDeliveryQueueName, restaurantToDeliveryExchangeName, restaurantToDeliveryRoutingKey);
            channel.queueDeclare(deliveryToCustomerQueueName, true, false, false, null);
            channel.queueBind(deliveryToCustomerQueueName, exchangeName, deliveryToCustomerRoutingKey);
            consumeRabbitMQMessage(rabbitmqHostName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void consumeRabbitMQMessage(String rabbitmqHostName) {
        try {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    ObjectMapper mapper = new ObjectMapper();
                    System.out.println("Deserializing JSON to Object:");
                    CustomerOrder customerOrder = mapper.readValue(message, CustomerOrder.class);
                    System.out.println("Received Delivery Update Message'" + customerOrder + "'");

                    deliveryService.deliverOrder(customerOrder.getId());

                    getRMQChannel(false, rabbitmqHostName).basicPublish(deliveryCustomerExchangeName, deliveryToCustomerRoutingKey, null, message.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e){
                    throw new RuntimeException("Error in consuming MQ message");
                }
            };
            getRMQChannel(true,rabbitmqHostName).basicConsume(restaurantToDeliveryQueueName, true, deliverCallback, consumerTag -> { });

        } catch (Exception e) {
            throw new RuntimeException("Error in consuming MQ message");
        }

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
}
