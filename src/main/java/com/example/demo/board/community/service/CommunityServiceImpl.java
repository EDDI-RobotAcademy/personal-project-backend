package com.example.demo.board.community.service;

import com.example.demo.board.community.controller.form.CommunityModifyForm;
import com.example.demo.board.community.controller.form.CommunityRegistForm;
import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.community.repository.CommunityRepository;
import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityServcie {

    final CommunityRepository communityRepository;

    // 커뮤니티 게시물 등록 기능
    @Override
    public CommunityBoard regist(CommunityRegistForm communityRegistForm) {
        CommunityBoard communityBoard = communityRegistForm.toCommunityBoard();
        Optional<CommunityBoard> maybeCommunityBoard = communityRepository.findByCommunityTitle(communityBoard.getCommunityTitle());
        if(maybeCommunityBoard.isPresent()){
            return null;
        }
        CommunityBoard saveCommunityBoard = communityRepository.save(communityBoard);

        return saveCommunityBoard;
    }

    // 커뮤니티 게시판 수정
    @Override
    public CommunityBoard modify(CommunityModifyForm communityModifyForm) {
        Optional<CommunityBoard> maybeCommunityBoard = communityRepository.findByCommunityNumber(communityModifyForm.getCommunityNumber());
        if (maybeCommunityBoard.isEmpty()) {
            log.info("에러 발생");
            return null;
        }
        CommunityBoard communityBoard = maybeCommunityBoard.get();
        communityBoard.setCommunityTitle(communityModifyForm.getCommunityTitle());
        communityBoard.setCommunityContent(communityModifyForm.getCommunityContent());

        return communityRepository.save(communityBoard);
    }

    // 커뮤니티 게시판 삭제
    @Override
    public Boolean delete(Long communityNumber) {
        Optional<CommunityBoard> maybeCommunityBoard = communityRepository.findByCommunityNumber(communityNumber);
        if (maybeCommunityBoard.isEmpty()){
            log.info("에러 발생");
            return false;
        }
        CommunityBoard communityBoard = maybeCommunityBoard.get();
        communityRepository.delete(communityBoard);

        return true;
    }

    // 커뮤니티 게시판 상세 정보
    @Override
    public CommunityBoard read(Long communityNumber) {
        Optional<CommunityBoard> maybeCommunityBoard = communityRepository.findByCommunityNumber(communityNumber);
        if (maybeCommunityBoard.isEmpty()){
            log.info("에러 발생");
            return null;
        }

        return maybeCommunityBoard.get();
    }

    // 커뮤니티 전체 게시판 목록
    @Override
    public List<CommunityBoard> list() {
        return communityRepository.findAll(Sort.by(Sort.Direction.DESC,"communityNumber"));
    }





}
