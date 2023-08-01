package com.example.demo.board.shop.controller.form;

import com.example.demo.board.shop.entity.ShopBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShopModifyForm {

    final private Long eventId;
    final private String title;
    final private String content;
    final private String image;

    public ShopBoard toShopBoard() {
        return new ShopBoard(eventId,title,content,image);
    }
}
