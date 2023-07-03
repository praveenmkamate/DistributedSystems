package com.ds.avengers;

public class Constants {
    public static final String exchangeName = "DeliveryAppExchange";
    public static final String cusToResQueueName = "customer_restaurant";
    public static final String cusToResRoutingKey = "customer_restaurant_key";
    public static final String cusToUIQueueName = "customer_ui";
    public static final String cusToUIRoutingKey = "customer_ui_key";

    public static final String deliveryCustomerExchangeName = "DeliveryCustomerApplicationExchange";
    public static final String restaurantToDeliveryExchangeName = "DeliveryRestaurantApplicationExchange";
    public static final String deliveryToCustomerQueueName = "customer_delivery";
    public static final String deliveryToCustomerRoutingKey = "customer_delivery_key";

    public static final String restaurantToDeliveryQueueName = "delivery_restaurant";
    public static final String restaurantToDeliveryRoutingKey = "delivery_restaurant_key";
    public enum orderStatus {ORDERED, ORDER_ACCEPTED, READY_FOR_PICKUP,OUT_FOR_DELIVERY, DELIVERED}
    
    public enum deliveryStatus {AVAILABLE,UNAVAILABLE,IN_PROGRESS}
}
