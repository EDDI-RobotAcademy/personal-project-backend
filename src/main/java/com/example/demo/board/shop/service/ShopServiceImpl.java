package com.example.demo.board.shop.service;

import com.example.demo.board.event.entity.EventBoard;
import com.example.demo.board.event.repository.EventRepository;
import com.example.demo.board.shop.entity.ShopBoard;
import com.example.demo.board.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    final ShopRepository shopRepository;

    // 상점 게시물 등록 기능
    @Override
    public ShopBoard regist(ShopBoard shopBoard) {
        Optional<ShopBoard> maybeShopBoard = shopRepository.findByShopId(shopBoard.getShopId());
        if(maybeShopBoard.isPresent()){
            return null;
        }
        ShopBoard saveShopBoard = shopRepository.save(shopBoard);

        return saveShopBoard;
    }

    // 상점 게시판 수정
    @Override
    public ShopBoard modify(ShopBoard shopBoard) {
        Optional<ShopBoard> maybeShopBoard = shopRepository.findByShopId(shopBoard.getShopId());
        if (maybeShopBoard.isEmpty()) {
            log.info("에러 발생");
            return null;
        }
        ShopBoard getShopBoard = maybeShopBoard.get();
        getShopBoard.setTitle(shopBoard.getTitle());
        getShopBoard.setContent(shopBoard.getContent());
        getShopBoard.setImage(shopBoard.getImage());

        return shopRepository.save(getShopBoard);
    }

    // 상점 게시판 삭제
    @Override
    public Boolean delete(Long shopId) {
        Optional<ShopBoard> maybeShopBoard = shopRepository.findByShopId(shopId);
        if (maybeShopBoard.isEmpty()){
            log.info("에러 발생");
            return false;
        }
        ShopBoard shopBoard = maybeShopBoard.get();
        shopRepository.delete(shopBoard);

        return true;
    }

    // 상점 게시판 상세 정보
    @Override
    public ShopBoard read(Long shopId) {
        Optional<ShopBoard> maybeShopBoard = shopRepository.findByShopId(shopId);
        if (maybeShopBoard.isEmpty()){
            log.info("에러 발생");
            return null;
        }

        return maybeShopBoard.get();
    }

    // 상점 전체 게시판 목록
    @Override
    public List<ShopBoard> list() {
        return shopRepository.findAll(Sort.by(Sort.Direction.DESC,"shopId"));
    }
}
