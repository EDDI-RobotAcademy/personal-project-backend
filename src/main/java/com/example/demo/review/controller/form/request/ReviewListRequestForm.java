package com.example.demo.review.controller.form.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReviewListRequestForm {

    @Getter
    private String restaurantName;
}
