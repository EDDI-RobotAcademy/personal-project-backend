package com.example.demo.restaurant.service.request;

import com.example.demo.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RestaurantRegisterRequest {
    final private String restaurantName;
    final private String restaurantInfo;
    final private String userToken;
    final private List<String> imageUrls;

    public Restaurant toRestaurant() {

        return new Restaurant(restaurantName, restaurantInfo);
    }
}
