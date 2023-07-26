package com.example.demo.restaurant.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food")
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    public Food(FoodType foodType) {
        this.foodType = foodType;
    }

    public FoodType getFoodType() {
        return foodType;
    }
}
