package com.example.demo.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Table(name = "menu")
@ToString
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String menuItem;
    @Getter
    @Setter
    private Integer menuPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @Setter
    private Restaurant restaurant;

    public Menu(String menuItem, Integer menuPrice) {
        this.menuItem = menuItem;
        this.menuPrice = menuPrice;
    }
}
