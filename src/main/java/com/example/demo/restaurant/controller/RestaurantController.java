package com.example.demo.restaurant.controller;

import com.example.demo.account.service.AccountService;
import com.example.demo.restaurant.controller.form.*;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantListRequestForm;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantReadResponseForm;
import com.example.demo.restaurant.entity.Restaurant;
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

    // 사업자 회원 - 음식점 등록
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

    // 맛집 상세 페이지
    @GetMapping("/{id}")
    public RestaurantReadResponseForm readRestaurant(@PathVariable("id") Long id) {
        log.info("readRestaurant()");

        return restaurantService.read(id);
    }

    // 사업자 회원 - 자신이 등록한 맛집 리스트
    @PostMapping("/business-restaurant-list")
    public List<BusinessRestaurantListResponseForm> businessRegisterProductList(@RequestBody BusinessRestaurantListRequestForm requestForm) {
        String userToken = requestForm.getUserToken();
        final Long accountId = accountService.findAccountId(userToken);

        List<BusinessRestaurantListResponseForm> responseList = restaurantService.businessRegisterRestaurantList(accountId);

        return responseList;
    }

    // 사업자 회원 - 맛집 상세 페이지
    @GetMapping("/business-restaurant/{id}")
    public BusinessRestaurantReadResponseForm readBusinessRestaurant(@PathVariable("id") Long id) {
        log.info("readBusinessRestaurant()");

        return restaurantService.businessRead(id);
    }

    // 사업자 회원 - 맛집 수정
    @PutMapping("/{id}")
    public Restaurant modifyRestaurant (@PathVariable("id") Long id,
                                        @RequestBody RestaurantModifyForm modifyForm) {
        log.info("modifyProduct()");

        return restaurantService.modify(id, modifyForm.toRestaurantModifyRequest());
    }

    // 사업자 회원 - 맛집 삭제
    @DeleteMapping("/{id}")
    public void deleteRestaurant (@PathVariable("id") Long id) {
        log.info("deleteRestaurant()");
        restaurantService.delete(id);
    }
}
