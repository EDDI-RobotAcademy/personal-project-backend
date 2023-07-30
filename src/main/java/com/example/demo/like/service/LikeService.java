package com.example.demo.like.service;

import com.example.demo.like.controller.form.LikeRestaurantCountForm;
import com.example.demo.like.controller.form.LikeRestaurantForm;

public interface LikeService {
    Boolean likeRestaurant(LikeRestaurantForm likeRestaurantForm);
    void delete(Long id, String userToken);
    Integer likesRestaurantCount(LikeRestaurantCountForm countForm);
}
