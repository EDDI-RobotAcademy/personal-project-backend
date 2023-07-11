package com.example.demo.restaurant.controller.form;

import com.example.demo.restaurant.service.request.RestaurantRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestaurantRegisterForm {
    final private String restaurantName;
    final private String restaurantInfo;
    final private String userToken;

    public RestaurantRegisterRequest toRestaurantRegisterRequest() {
        return new RestaurantRegisterRequest(restaurantName, restaurantInfo, userToken);
    }

}
