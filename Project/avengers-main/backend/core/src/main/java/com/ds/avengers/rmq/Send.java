package com.ds.avengers.rmq;

import com.ds.avengers.dto.CustomerOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

import static com.ds.avengers.Constants.*;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            CustomerOrder co = new CustomerOrder();
            co.setRestaurant_id(1);
            co.setCustomer_id(17);
            co.setDelivery_address("D02Y111");
            co.setId("1672357200155 ");
            //Creating the ObjectMapper object
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString
            String message = mapper.writeValueAsString(co);
//            String message = "Hello World!";
            channel.basicPublish(exchangeName, deliveryToCustomerRoutingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}