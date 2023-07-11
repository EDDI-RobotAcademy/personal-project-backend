package com.example.demo.restaurant.service;

import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantReadResponseForm;
import com.example.demo.restaurant.service.request.RestaurantRegisterRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {
    List<RestaurantListResponseForm> list();
    Boolean register(RestaurantRegisterRequest restaurantRegisterRequest, List<MultipartFile> restaurantImg);
    @Transactional
    RestaurantReadResponseForm read(Long id);
}
