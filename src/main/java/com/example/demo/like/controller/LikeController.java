package com.example.demo.like.controller;

import com.example.demo.like.controller.form.LikeRestaurantCountForm;
import com.example.demo.like.controller.form.LikeRestaurantForm;
import com.example.demo.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    final private LikeService likeService;

    // 찜 등록
    @PostMapping("/restaurant")
    public Boolean likeRestaurant (@RequestBody LikeRestaurantForm likeRestaurantForm) {
        log.info("likeRestaurant()");

        return likeService.likeRestaurant(likeRestaurantForm);
    }

    // 찜 해제
    @DeleteMapping("/{id}")
    public void deleteLikedRestaurant (@PathVariable("id") Long id,
                                       @RequestParam("userToken") String userToken) {
        log.info("deleteLikedRestaurant()");

        likeService.delete(id, userToken);
    }

    // 찜 숫자 반환
    @PostMapping("/restaurant-count")
    public Integer countLikedRestaurant (@RequestBody LikeRestaurantCountForm countForm) {
        log.info("countLikedRestaurant()");

        final Integer likeCount = likeService.likesRestaurantCount(countForm);

        return likeCount;
    }
}
