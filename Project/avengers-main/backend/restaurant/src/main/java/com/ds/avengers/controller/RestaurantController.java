package com.ds.avengers.controller;

import com.ds.avengers.dto.CustomerOrder;
import com.ds.avengers.dto.MenuItems;
import com.ds.avengers.dto.Restaurant;
import com.ds.avengers.dto.RestaurantMenu;
import com.ds.avengers.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "restaurant/api/v1")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RequestMapping(value = "/activeRestaurants", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> activeRestaurants() {
        return this.restaurantService.activeRestaurants();
    }

    @RequestMapping(value = "/getMenu/{restaurantId}", method = RequestMethod.GET)
    public ResponseEntity<List<MenuItems>> getMenu(@PathVariable("restaurantId") Integer restaurantId) {
        return this.restaurantService.getMenu(restaurantId);
    }

    @RequestMapping(value = "/addItemsToMenu/{restaurantId}", method = RequestMethod.POST)
    public ResponseEntity<List<MenuItems>> addItemsToMenu(@PathVariable("restaurantId") Integer restaurantId,
                                                          @RequestBody MenuItems menuItem) {
        return this.restaurantService.addItemsToMenu(restaurantId, menuItem);
    }

    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.PUT)
    public ResponseEntity<String> updateOrderStatus(@RequestBody CustomerOrder customerOrder) {
        return this.restaurantService.updateOrderStatus(customerOrder);
    }

    @RequestMapping(value = "/getOrders/{restaurantId}", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerOrder>> getOrders(@PathVariable("restaurantId") Integer restaurantId) {
        return this.restaurantService.getOrders(restaurantId);
    }

    @RequestMapping(value = "/restaurants/menus/items", method = RequestMethod.GET)
    public ResponseEntity<List<RestaurantMenu>> getRestaurantMenus() {
        return this.restaurantService.getRestaurantMenus();
    }

    @RequestMapping(value = "/restaurants/menus/items/{restaurantId}", method = RequestMethod.GET)
    public ResponseEntity<List<RestaurantMenu>> getRestaurantMenus(@PathVariable("restaurantId") Integer restaurantId) {
        return this.restaurantService.getRestaurantMenus(restaurantId);
    }
}
