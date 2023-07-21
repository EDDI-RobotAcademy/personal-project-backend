package com.example.demo.review.controller.form;

import com.example.demo.review.service.request.ReviewRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRegisterForm {

    private Float ratings;
    // Todo: 이미지 추가하기
//    private List<String> imageUrls;
    private String comment;
    private String userToken;
    private String restaurantName;

    public ReviewRegisterRequest toReviewRegisterRequest() {
        return new ReviewRegisterRequest(ratings, comment, userToken, restaurantName);
    }
}
