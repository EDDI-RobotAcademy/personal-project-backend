//package com.example.demo.review.entity;
//
//import com.example.demo.restaurant.entity.Restaurant;
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@ToString(exclude = {"review"})
//@Entity
//@NoArgsConstructor
//public class ReviewImages {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Getter
//    private String reviewImageResourcePath;
//
//    @Setter
//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "review_id")
//    private Restaurant review;
//
//    public ReviewImages(String reviewImageResourcePath) {
//        this.reviewImageResourcePath = reviewImageResourcePath;
//    }
//}
