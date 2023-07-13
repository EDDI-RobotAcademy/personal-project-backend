package com.example.demo.restaurant.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class BusinessRestaurantListResponseForm {

    final String restaurantName;
    final String restaurantInfo;
    final String restaurantImagePath;
}
