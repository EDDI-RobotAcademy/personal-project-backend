package com.example.demo.like.entity;

import com.example.demo.account.entity.Account;
import com.example.demo.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
//    @Setter
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
//    @Setter
    private Account account;

    public LikeEntity(Account account, Restaurant restaurant) {
        this.account = account;
        this.restaurant = restaurant;
    }
}
