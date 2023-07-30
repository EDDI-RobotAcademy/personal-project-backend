package com.example.demo.review.service.request;

import com.example.demo.review.entity.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewRegisterRequest {

    final private Float ratings;
    // Todo: 이미지 추가하기
//    private List<String> imageUrls;
    final private String comment;
    final private String userToken;
    final private Long restaurantId;

    public Review toReview() {
        return new Review(ratings, comment);
    }
}
