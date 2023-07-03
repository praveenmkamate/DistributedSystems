package com.ds.avengers.service;

import com.ds.avengers.dto.*;
import com.ds.avengers.repo.*;
import com.ds.avengers.rmq.RMQConnector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;;
import java.util.List;

import static com.ds.avengers.Constants.*;

@Service
public class RestaurantService {

    private final IRestaurantRepo restaurantRepo;
    private final IMenuRepo menuRepo;
    private final IMenuItemsRepo menuItemsRepo;

    private final ICustomerOrderRepo customerOrderRepo;

    private final IRestaurantMenuRepo restaurantMenuRepo;

    private Channel channel;

    public static RMQConnector rmqConnector = new RMQConnector();

    @Autowired
    RestaurantService(IRestaurantRepo restaurantRepo, IMenuRepo menuRepo,
                      IMenuItemsRepo menuItemsRepo, ICustomerOrderRepo customerOrderRepo,
                      IRestaurantMenuRepo restaurantMenuRepo, @Value("${spring.rabbitmq.host}") String rabbitmqHostName) {
        this.restaurantRepo = restaurantRepo;
        this.menuRepo = menuRepo;
        this.menuItemsRepo = menuItemsRepo;
        this.customerOrderRepo = customerOrderRepo;
        this.restaurantMenuRepo = restaurantMenuRepo;
        connectRabbitMq(rabbitmqHostName);
    }

    /** Return list of available active restaurants **/
    public ResponseEntity<List<Restaurant>> activeRestaurants() {
        try {
            return new ResponseEntity<>(this.restaurantRepo.activeRestaurants(), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    /** Update the order status of the customer order **/
    public ResponseEntity<String> updateOrderStatus(CustomerOrder customerOrder) {
        String message = "Order Status Updated";
        try {
            this.customerOrderRepo.save(customerOrder);
            if (channel != null) {
                sendRabbitMQMessage(customerOrder);
            }
            return new ResponseEntity<>(message, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    /** Return menu of the specified restaurant **/
    public ResponseEntity<List<MenuItems>> getMenu(Integer restaurantId) {
        try {
            Menu menu = this.menuRepo.getMenu(restaurantId);
            List<MenuItems> menuItems = this.menuItemsRepo.getMenuItems(menu.getId());
            return new ResponseEntity<>(menuItems, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }

    }

    /** Add items to the menu for the specified restaurant **/
    public ResponseEntity<List<MenuItems>> addItemsToMenu(Integer restaurantId, MenuItems menuItem) {
        try {
            menuItem.setMenu_id(this.menuRepo.getMenu(restaurantId).getId().intValue());
            this.menuItemsRepo.save(menuItem);
            return getMenu(restaurantId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    /** Return list of customer orders for the specified restaurants **/
    public ResponseEntity<List<CustomerOrder>> getOrders(Integer restaurantId) {
        try {
            List<CustomerOrder> customerOrders = this.customerOrderRepo.getOrdersByRestaurant(restaurantId);
            return new ResponseEntity<>(customerOrders, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    /** Return list of all the available restaurant menus **/
    public ResponseEntity<List<RestaurantMenu>> getRestaurantMenus() {
        try {
            List<RestaurantMenu> restaurantMenus = this.restaurantMenuRepo.findAll();
            return new ResponseEntity<>(restaurantMenus, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    /** Return list of restaurant menus for specified restaurant **/
    public ResponseEntity<List<RestaurantMenu>> getRestaurantMenus(Integer restaurantId) {
        try {
            List<RestaurantMenu> restaurantMenus = this.restaurantMenuRepo.getItemsByRestaurant(restaurantId);
            return new ResponseEntity<>(restaurantMenus, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    private void connectRabbitMq(String rabbitmqHostName) {
        try {
            Connection connection = rmqConnector.getRMQConnection("RestaurantService", rabbitmqHostName);
            channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.exchangeDeclare(restaurantToDeliveryExchangeName, "direct", true);
            channel.queueDeclare(restaurantToDeliveryQueueName, true, false, false, null);
            channel.queueBind(restaurantToDeliveryQueueName, restaurantToDeliveryExchangeName,
                    restaurantToDeliveryRoutingKey);
        } catch (Exception e) {
            System.out.println("Real time updates will not work");
        }
    }

    private void sendRabbitMQMessage(CustomerOrder customerOrder) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            channel.basicPublish(restaurantToDeliveryExchangeName, restaurantToDeliveryRoutingKey, null,
                    mapper.writeValueAsString(customerOrder).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
