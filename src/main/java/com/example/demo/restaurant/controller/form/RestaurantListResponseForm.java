package com.example.demo.restaurant.controller.form;

import com.example.demo.restaurant.entity.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantListResponseForm {
    final private Long id;
    final private String restaurantName;
    final private String restaurantInfo;
    final private String restaurantImagePath;

    public RestaurantListResponseForm(Restaurant restaurant, String restaurantImagePath) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.restaurantInfo = restaurant.getRestaurantInfo();
        this.restaurantImagePath = restaurantImagePath;
    }
}
