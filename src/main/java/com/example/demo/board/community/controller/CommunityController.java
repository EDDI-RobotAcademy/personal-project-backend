package com.example.demo.board.community.controller;

import com.example.demo.board.community.controller.form.CommunityModifyForm;
import com.example.demo.board.community.controller.form.CommunityRegistForm;
import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.community.service.CommunityServcie;
import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    final CommunityServcie communityServcie;

    // 커뮤니티 게시물 등록 기능
    @PostMapping("/regist")
    public String communityRegist(CommunityRegistForm communityRegistForm){
        log.info("CommunityRegist() ");
        CommunityBoard communityBoard = communityServcie.regist(communityRegistForm.toCommunityBoard());
        if (communityBoard == null){
            return null;
        }

        return communityBoard.getTitle();
    }

    // 커뮤니티 게시물 목록 확인 기능
    @GetMapping("/list")
    public List<CommunityBoard> communityList(){
        log.info("CommunityList() ");
        List<CommunityBoard> returnedCommunityList = communityServcie.list();
        log.info("CommunityList : " + returnedCommunityList);

        return returnedCommunityList;
    }

    // 커뮤니티 게시물 상세 정보 확인
    @GetMapping("/list/{communityNumber}")
    public CommunityBoard communityRead(@PathVariable Long communityId){
        log.info("CommunityRead() ");
        CommunityBoard readCommunityBoard = communityServcie.read(communityId);

        return readCommunityBoard;
    }

    // 커뮤니티 게시판 수정 기능
    @PutMapping("/modify")
    public Long communityModify(@RequestBody CommunityModifyForm communityModifyForm){
        log.info("CommunityModify() ");
        CommunityBoard communityBoard = communityServcie.modify(communityModifyForm.toCommunityBoard());

        return communityBoard.getCommunityId();
    }

    //커뮤니티 게시판 삭제 기능
    @DeleteMapping("/delete")
    public boolean communityDelete(@RequestParam("communityId") Long communityId){
        log.info("CommunityDelete() ");
        boolean resultDeleteCommunity = communityServcie.delete(communityId);

        return resultDeleteCommunity;
    }

}
