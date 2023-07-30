package com.example.demo.restaurant.controller.form;

import com.example.demo.restaurant.entity.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RestaurantModifyResponseForm {

    final private Long id;
    final private String restaurantName;
    final private String restaurantInfo;
    final private String restaurantAddress;
    final private Integer restaurantNumber;
    final private String restaurantTime;
    final private List<String> restaurantImagesPathList = new ArrayList<>();
    final private String menuItem;
    final private Integer menuPrice;
    final private FoodType foodType;

    public RestaurantModifyResponseForm(Restaurant restaurant, List<RestaurantImages> restaurantImagesList,
                                      Menu menu, RestaurantFood restaurantFood) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.restaurantInfo = restaurant.getRestaurantInfo();
        this.restaurantAddress = restaurant.getRestaurantAddress();
        this.restaurantNumber = restaurant.getRestaurantNumber();
        this.restaurantTime = restaurant.getRestaurantTime();

        this.menuItem = menu.getMenuItem();
        this.menuPrice = menu.getMenuPrice();

        this.foodType = restaurantFood.getFood().getFoodType();

        for (RestaurantImages images: restaurantImagesList) {
            this.restaurantImagesPathList.add(images.getImageResourcePath());
        }
    }
}
