package com.example.demo.restaurant.entity;

import com.example.demo.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String restaurantInfo;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<RestaurantImages> restaurantImagesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    @Setter
    private Account account;

    public Restaurant(String restaurantName, String restaurantInfo) {
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
    }
}
