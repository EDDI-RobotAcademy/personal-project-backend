package com.example.demo.board.shop.controller.form;

import com.example.demo.board.shop.entity.ShopBoard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopRegistForm {
    final private String title;
    final private String content;
    final private Long accountId;
    final private String image;

    public ShopBoard toShopBoard() {
        return new ShopBoard(title, content,accountId,image);
    }
}
