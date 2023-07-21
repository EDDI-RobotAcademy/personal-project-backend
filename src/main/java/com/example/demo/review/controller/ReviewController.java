package com.example.demo.review.controller;

import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.service.RestaurantService;
import com.example.demo.review.controller.form.ReviewRegisterForm;
import com.example.demo.review.controller.form.request.ReviewListRequestForm;
import com.example.demo.review.controller.form.response.ReviewListResponseForm;
import com.example.demo.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    final private ReviewService reviewService;
    final private RestaurantService restaurantService;

    // 리뷰 등록
    @PostMapping("/register")
    public Boolean registerReview (@RequestBody ReviewRegisterForm registerForm) {
        log.info("registerReview()");

        return reviewService.register(registerForm.toReviewRegisterRequest());
    }

    // 리뷰 리스트 띄우기
    @PostMapping("/list")
    public List<ReviewListResponseForm> reviewList(@RequestBody ReviewListRequestForm requestForm) {
        log.info("reviewList()");

        final String restaurantName = requestForm.getRestaurantName();
        final Long restaurantId = restaurantService.findRestaurantId(restaurantName);

        List<ReviewListResponseForm> reviewList = reviewService.list(restaurantId);

        return reviewList;
    }
}
