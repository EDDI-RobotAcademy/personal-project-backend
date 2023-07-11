package com.example.demo.shopTest;

import com.example.demo.board.event.controller.form.EventModifyForm;
import com.example.demo.board.event.controller.form.EventRegistForm;
import com.example.demo.board.event.entity.EventBoard;
import com.example.demo.board.event.repository.EventRepository;
import com.example.demo.board.event.service.EventService;
import com.example.demo.board.shop.controller.form.ShopModifyForm;
import com.example.demo.board.shop.controller.form.ShopRegistForm;
import com.example.demo.board.shop.entity.ShopBoard;
import com.example.demo.board.shop.repository.ShopRepository;
import com.example.demo.board.shop.service.ShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShopTest {
    @Autowired
    ShopService shopService;
    @Autowired
    ShopRepository shopRepository;

    @Test
    @DisplayName("상점 게시물 생성")
    void 게시물_생성(){
        final String title = "상점 제목1";
        final String content = "상점 내용1";
        final Long accountId = 1L;
        final String image = "love.jpg";
        ShopRegistForm shopRegistForm = new ShopRegistForm(title,content,accountId,image);
        ShopBoard shopBoard = shopService.regist(shopRegistForm.toShopBoard());
        long shopId = shopBoard.getShopId();
        ShopBoard DBshop = shopRepository.findByShopId(shopId).get();

        assertEquals(title,DBshop.getTitle());
        assertEquals(content,DBshop.getContent());
        assertEquals(accountId,DBshop.getAccount().getAccountId());
        assertEquals(image,DBshop.getImage());

    }

    @Test
    @DisplayName("상점 게시물을 수정합니다.")
    void 게시물_수정(){
        final Long shopId = 3L;
        final String title = "변경 제목1";
        final String content = "변경 내용1";
        final String image = "change_image.jpg";
        ShopModifyForm shopModifyForm = new ShopModifyForm(shopId,title,content,image);
        ShopBoard shopBoard = shopModifyForm.toShopBoard();
        shopService.modify(shopBoard);
        ShopBoard DBshop = shopRepository.findByShopId(shopId).get();

        assertEquals(title,DBshop.getTitle());
        assertEquals(content,DBshop.getContent());
        assertEquals(image,DBshop.getImage());
    }

    @Test
    @DisplayName("상점 게시판 목록 확인")
    void 게시판_목록_확인 (){
        List<ShopBoard> shopBoardList = shopService.list();

        for (ShopBoard shopBoard: shopBoardList){
            System.out.println("===============");
            System.out.println(shopBoard.getShopId());
            System.out.println(shopBoard.getTitle());
            System.out.println(shopBoard.getDate());
            System.out.println(shopBoard.getAccount().getAccountId());
            System.out.println(shopBoard.getImage());

            assertTrue(shopBoard.getShopId() != null);
            assertTrue(shopBoard.getTitle() != null);
            assertTrue(shopBoard.getDate() != null);
            assertTrue(shopBoard.getAccount().getAccountId() != null);
            assertTrue(shopBoard.getImage() != null);
        }
    }

    @Test
    @DisplayName("상점 게시물 상세 정보 확인")
    void 게시물_정보_확인(){
        final Long shopId = 3L;
        ShopBoard shopBoard = shopService.read(shopId);
        ShopBoard DBshop = shopRepository.findByShopId(shopId).get();
        System.out.println(shopBoard.getShopId());
        System.out.println(shopBoard.getTitle());
        System.out.println(shopBoard.getContent());
        System.out.println(shopBoard.getAccount().getAccountId());
        System.out.println(shopBoard.getImage());

        assertEquals(shopBoard.getShopId(),DBshop.getShopId());
        assertEquals(shopBoard.getTitle(),DBshop.getTitle());
        assertEquals(shopBoard.getContent(),DBshop.getContent());
        assertEquals(shopBoard.getDate(),DBshop.getDate());
        assertEquals(shopBoard.getAccount().getAccountId(),DBshop.getAccount().getAccountId());
        assertEquals(shopBoard.getImage(),DBshop.getImage());
    }

    @Test
    @DisplayName("상점 게시물을 삭제합니다.")
    void 게시물_삭제(){
        final Long shopId = 3L;
        Boolean resultShopDelete = shopService.delete(shopId);

        assertTrue(resultShopDelete);
    }
}
