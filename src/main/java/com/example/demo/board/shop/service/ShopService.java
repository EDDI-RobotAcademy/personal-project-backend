package com.example.demo.board.shop.service;

import com.example.demo.board.shop.entity.ShopBoard;

import java.util.List;

public interface ShopService {

    List<ShopBoard> list();

    ShopBoard regist(ShopBoard shopBoard);

    ShopBoard modify(ShopBoard shopBoard);

    Boolean delete(Long shopId);

    ShopBoard read(Long shopId);
}
