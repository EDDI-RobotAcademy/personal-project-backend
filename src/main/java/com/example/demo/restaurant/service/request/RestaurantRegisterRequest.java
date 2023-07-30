package com.example.demo.restaurant.service.request;

import com.example.demo.restaurant.controller.form.MenuData;
import com.example.demo.restaurant.entity.Food;
import com.example.demo.restaurant.entity.FoodType;
import com.example.demo.restaurant.entity.Menu;
import com.example.demo.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class RestaurantRegisterRequest {
    final private String restaurantName;
    final private String restaurantInfo;
    final private String restaurantAddress;
    final private Integer restaurantNumber;
    final private String restaurantTime;
    final private List<MenuData> menus;
    final private FoodType foodType;

    final private String userToken;
    final private List<String> imageUrls;

    public Restaurant toRestaurant() {

        return new Restaurant(restaurantName, restaurantInfo, restaurantAddress,
                restaurantNumber, restaurantTime);
    }

    public Menu toMenu() {
        List<MenuData> menuDataList = this.getMenus();
        List<Menu> menuList = new ArrayList<>();

        for (MenuData menuData : menuDataList) {
            Menu menu = new Menu(menuData.getMenuItem(), menuData.getMenuPrice());
            menuList.add(menu);
        }

        return (Menu) menuList;
    }
}
