package com.example.demo.review.controller.form.response;

import com.example.demo.account.entity.Account;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.entity.RestaurantImages;
import com.example.demo.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewReadResponseForm {

    final private Long id;
    final private Float ratings;
    final private String comment;
//    final private List<String> reviewImagesPathList = new ArrayList<>();
    final private Long accountId;
    final private LocalDateTime createDate;
    final private LocalDateTime updateDate;

    public ReviewReadResponseForm(Review review) {
        this.id = review.getId();
        this.ratings = review.getRatings();
        this.comment = review.getComment();
        this.accountId = review.getAccount().getId();
        this.createDate = review.getCreateDate();
        this.updateDate = review.getUpdateDate();

//        for (ReviewImages images: reviewImagesList) {
//            this.reviewImagesPathList.add(images.getImageResourcePath());
//        }
    }
}
