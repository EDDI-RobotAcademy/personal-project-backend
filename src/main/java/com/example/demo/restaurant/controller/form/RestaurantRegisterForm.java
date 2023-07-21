package com.example.demo.restaurant.controller.form;

import com.example.demo.restaurant.service.request.RestaurantRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRegisterForm {
    private String restaurantName;
    private String restaurantInfo;
    private String userToken;
    private List<String> imageUrls;

    public RestaurantRegisterRequest toRestaurantRegisterRequest() {
        return new RestaurantRegisterRequest(restaurantName, restaurantInfo, userToken, imageUrls);
    }
}
