package com.example.demo.board.shop.controller;

import com.example.demo.board.shop.controller.form.ShopModifyForm;
import com.example.demo.board.shop.controller.form.ShopRegistForm;
import com.example.demo.board.shop.entity.ShopBoard;
import com.example.demo.board.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    final ShopService shopService;

    // 상점 게시물 등록 기능
    @PostMapping("/regist")
    public String shopRegist(ShopRegistForm shopRegistForm){
        log.info("ShopRegist() ");
        ShopBoard shopBoard = shopService.regist(shopRegistForm.toShopBoard());
        if (shopBoard == null){
            return null;
        }

        return shopBoard.getTitle();
    }

    // 상점 게시판 수정
    @PutMapping("/modify")
    public Long shopModify(@RequestBody ShopModifyForm shopModifyForm){
        log.info("ShopModify() ");
        ShopBoard shopBoard = shopService.modify(shopModifyForm.toShopBoard());

        return shopBoard.getShopId();
    }

    // 상점 게시물 목록 확인
    @GetMapping("/list")
    public List<ShopBoard> shopList(){
        log.info("ShopList() ");
        List<ShopBoard> returnedShopList = shopService.list();
        log.info("ShopList : " + returnedShopList);

        return returnedShopList;
    }

    // 상점 게시물 읽기
    @GetMapping("/list/{shopId}")
    public ShopBoard shopRead(@PathVariable Long shopId){
        log.info("ShopRead() ");
        ShopBoard readShopBoard = shopService.read(shopId);

        return readShopBoard;
    }


    // 상점 게시물 삭제
    @DeleteMapping("/delete")
    public boolean shopDelete(@RequestParam("shopId") Long shopId){
        log.info("ShopDelete() ");
        boolean resultDeleteShop = shopService.delete(shopId);

        return resultDeleteShop;
    }

}
