package com.example.demo.restaurant.controller.form;

import com.example.demo.restaurant.entity.FoodType;
import com.example.demo.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantModifyForm {

    private String restaurantName;
    private String restaurantInfo;
    private String restaurantAddress;
    private Integer restaurantNumber;
    private String restaurantTime;

    private String menuItem;
    private Integer menuPrice;
    private FoodType foodType;

    private String userToken;
    private List<String> imageUrls;

    public Restaurant toRestaurant() {

        return new Restaurant(restaurantName, restaurantInfo, restaurantAddress,
                restaurantNumber, restaurantTime);
    }
}
