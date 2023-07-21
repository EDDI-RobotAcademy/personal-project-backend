package com.example.demo.restaurant.controller.form.business;

import com.example.demo.restaurant.entity.Restaurant;
import lombok.Getter;

@Getter
public class BusinessRestaurantListResponseForm {

    final private Long id;
    final private String restaurantName;
    final private String restaurantInfo;
    final private String restaurantImagePath;

    public BusinessRestaurantListResponseForm(Restaurant restaurant, String restaurantImagePath) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.restaurantInfo = restaurant.getRestaurantInfo();
        this.restaurantImagePath = restaurantImagePath;
    }
}
