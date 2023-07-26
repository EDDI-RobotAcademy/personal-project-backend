package com.example.demo.restaurant.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class RestaurantFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_Id")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_Id")
    private Restaurant restaurant;

    public RestaurantFood(Food food, Restaurant restaurant) {
        this.food = food;
        this.restaurant = restaurant;
    }
}
