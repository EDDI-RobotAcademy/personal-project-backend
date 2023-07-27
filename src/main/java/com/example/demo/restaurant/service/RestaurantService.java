package com.example.demo.restaurant.service;

import com.example.demo.restaurant.controller.form.RestaurantModifyForm;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantReadResponseForm;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantReadResponseForm;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.service.request.RestaurantRegisterRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RestaurantService {
    List<RestaurantListResponseForm> list();
    @Transactional
    RestaurantReadResponseForm read(Long id);
    List<BusinessRestaurantListResponseForm> businessRegisterRestaurantList(Long accountId);
    @Transactional
    BusinessRestaurantReadResponseForm businessRead(Long id);
    void delete(Long id);
    Boolean register(RestaurantRegisterRequest restaurantRegisterRequest, List<String> imageUrls);
    Restaurant modify(Long id, RestaurantModifyForm modifyForm);
    Integer returnVisitor(Long id);
}
