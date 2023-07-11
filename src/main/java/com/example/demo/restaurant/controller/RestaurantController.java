package com.example.demo.restaurant.controller;

import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.AccountService;
import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantReadResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantRegisterForm;
import com.example.demo.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.demo.account.entity.RoleType.BUSINESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    final private RestaurantService restaurantService;
    final private AccountService accountService;

    // 음식점 리스트 띄우기
    @GetMapping("/list")
    public List<RestaurantListResponseForm> restaurantList() {
        log.info("restaurantList()");

        List<RestaurantListResponseForm> restaurantList = restaurantService.list();

        return restaurantList;
    }

    // 사업자 회원 음식점 등록
    @PostMapping(value = "/register",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                        MediaType.APPLICATION_JSON_VALUE})
    public Boolean registerRestaurant (@RequestPart(value = "aboutRestaurant") RestaurantRegisterForm registerForm,
                                       @RequestPart(value = "restaurantImg") List<MultipartFile> restaurantImg) {

        log.info("registerRestaurant()");
        if (accountService.lookup(registerForm.getUserToken()) != BUSINESS) {
            return false;
        }

        return restaurantService.register(registerForm.toRestaurantRegisterRequest(), restaurantImg);
    }

    // 음식점 상세 페이지
    @GetMapping("/{id}")
    public RestaurantReadResponseForm readRestaurant(@PathVariable("id") Long id) {
        log.info("readRestaurant()");

        return restaurantService.read(id);
    }
}
