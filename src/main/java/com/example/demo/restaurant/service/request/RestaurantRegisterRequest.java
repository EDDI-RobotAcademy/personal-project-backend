package com.example.demo.restaurant.service.request;

import com.example.demo.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestaurantRegisterRequest {
    final private String restaurantName;
    final private String restaurantInfo;
    final private String userToken;


    public Restaurant toRestaurant() {

        return new Restaurant(restaurantName, restaurantInfo);
    }

}
