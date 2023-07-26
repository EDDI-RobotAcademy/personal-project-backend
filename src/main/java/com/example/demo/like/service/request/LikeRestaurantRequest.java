package com.example.demo.like.service.request;

import com.example.demo.account.entity.Account;
import com.example.demo.like.entity.LikeEntity;
import com.example.demo.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LikeRestaurantRequest {

    final private String userToken;
    final private Long restaurantId;

    public LikeEntity toLike(Account account, Restaurant restaurant) {
        return new LikeEntity(account, restaurant);
    }
}
