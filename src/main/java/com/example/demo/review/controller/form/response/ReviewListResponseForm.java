package com.example.demo.review.controller.form.response;

import com.example.demo.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewListResponseForm {

    private Long id;
    private Float ratings;
    private String comment;
    // Todo: 이미지 추가하기
//    private List<String> imageUrls;

    public ReviewListResponseForm(Review review) {
        this.id = review.getId();
        this.ratings = review.getRatings();
        this.comment = review.getComment();
    }
}
