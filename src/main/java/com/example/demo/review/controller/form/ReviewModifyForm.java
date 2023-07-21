package com.example.demo.review.controller.form;

import com.example.demo.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewModifyForm {

    private String userToken;
    private Float ratings;
    private String comment;

    public Review toReview() {
        return new Review(ratings, comment);
    }
}
