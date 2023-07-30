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
    @Setter
    private String restaurantName;
    @Setter
    private String restaurantInfo;
    @Setter
    private String restaurantAddress;
    @Setter
    private Integer restaurantNumber;
    @Setter
    private String restaurantTime;
    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @Setter
    private List<RestaurantImages> restaurantImagesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @Setter
    private Account account;

    public Restaurant(String restaurantName, String restaurantInfo, String restaurantAddress, Integer restaurantNumber, String restaurantTime) {
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.restaurantAddress = restaurantAddress;
        this.restaurantNumber = restaurantNumber;
        this.restaurantTime = restaurantTime;
    }

    public void setRestaurantImages(RestaurantImages restaurantImg) {
        restaurantImg.setRestaurant(this);
        restaurantImagesList.add(restaurantImg);
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
