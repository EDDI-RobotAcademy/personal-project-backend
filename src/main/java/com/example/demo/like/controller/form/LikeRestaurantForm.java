package com.example.demo.like.controller.form;

import com.example.demo.account.entity.Account;
import com.example.demo.like.entity.LikeEntity;
import com.example.demo.like.service.request.LikeRestaurantRequest;
import com.example.demo.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LikeRestaurantForm {

    private String userToken;
    private Long restaurantId;

//    public LikeRestaurantRequest toLikeRegisterRequest() {
//        return new LikeRestaurantRequest(userToken, restaurantId);
//    }

    public LikeEntity toLike(Account account, Restaurant restaurant) {
        return new LikeEntity(account, restaurant);
    }
}
