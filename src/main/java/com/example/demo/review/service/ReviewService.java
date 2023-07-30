package com.example.demo.review.service;

import com.example.demo.review.controller.form.ReviewModifyForm;
import com.example.demo.review.controller.form.request.ReviewAverageRatingsForm;
import com.example.demo.review.controller.form.request.ReviewListRequestForm;
import com.example.demo.review.controller.form.response.ReviewListResponseForm;
import com.example.demo.review.controller.form.response.ReviewReadResponseForm;
import com.example.demo.review.entity.Review;
import com.example.demo.review.service.request.ReviewRegisterRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ReviewService {
    Boolean register(ReviewRegisterRequest reviewRegisterRequest);
    Review modify(Long id, ReviewModifyForm modifyForm);
    ReviewReadResponseForm read(Long id);
    void delete(Long id);
    Float averageRatings(ReviewAverageRatingsForm averageRatingsForm);
    List<ReviewListResponseForm> list(ReviewListRequestForm requestForm);
}
