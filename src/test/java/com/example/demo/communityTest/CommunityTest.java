package com.example.demo.communityTest;

import com.example.demo.board.community.controller.form.CommunityModifyForm;
import com.example.demo.board.community.controller.form.CommunityRegistForm;
import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.community.repository.CommunityRepository;
import com.example.demo.board.community.service.CommunityServcie;
import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.repository.NoticeRepository;
import com.example.demo.board.notice.service.NoticeService;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommunityTest {
    @Autowired
    CommunityServcie communityServcie;
    @Autowired
    CommunityRepository communityRepository;

    @Test
    @DisplayName("커뮤니티 게시물 생성")
    void 게시물_생성(){
        final String title = "커뮤니티 제목1";
        final String content = "커뮤니티 내용1";
        final Long accountId = 1L;
//        CommunityRegistForm communityRegistForm = new CommunityRegistForm(title,content,accountId);
        CommunityRegistForm communityRegistForm = new CommunityRegistForm(title,content);
        CommunityBoard communityBoard = communityServcie.regist(communityRegistForm.toCommunityBoard());
        long communityId = communityBoard.getCommunityId();
        CommunityBoard DBcommunity = communityRepository.findByCommunityId(communityId).get();

        assertEquals(title,DBcommunity.getTitle());
        assertEquals(content,DBcommunity.getContent());
        assertEquals(accountId,DBcommunity.getAccount().getAccountId());
    }

    @Test
    @DisplayName("커뮤니티 게시물을 수정합니다.")
    void 게시물_수정(){
        final Long communityId = 3L;
        final String title = "변경 제목1";
        final String content = "변경 내용1";
        CommunityModifyForm communityModifyForm = new CommunityModifyForm(communityId,title,content);
        CommunityBoard communityBoard = communityModifyForm.toCommunityBoard();
        communityServcie.modify(communityBoard);
        CommunityBoard DBcommunity = communityRepository.findByCommunityId(communityId).get();

        assertEquals(title,DBcommunity.getTitle());
        assertEquals(content,DBcommunity.getContent());
    }

    @Test
    @DisplayName("커뮤니티 게시판 목록 확인")
    void 게시판_목록_확인 (){
        List<CommunityBoard> communityBoardList = communityServcie.list();

        for (CommunityBoard communityBoard: communityBoardList){
            System.out.println("===============");
            System.out.println(communityBoard.getCommunityId());
            System.out.println(communityBoard.getTitle());
            System.out.println(communityBoard.getDate());
            System.out.println(communityBoard.getAccount().getAccountId());

            assertTrue(communityBoard.getCommunityId() != null);
            assertTrue(communityBoard.getTitle() != null);
            assertTrue(communityBoard.getDate() != null);
            assertTrue(communityBoard.getAccount().getAccountId() != null);
        }
    }

    @Test
    @DisplayName("커뮤니티 게시물 상세 정보 확인")
    void 게시물_정보_확인(){
        final Long communityId = 3L;
        CommunityBoard communityBoard = communityServcie.read(communityId);
        CommunityBoard DBcommunity = communityRepository.findByCommunityId(communityId).get();
        System.out.println(communityBoard.getCommunityId());
        System.out.println(communityBoard.getTitle());
        System.out.println(communityBoard.getContent());
        System.out.println(communityBoard.getAccount().getAccountId());

        assertEquals(communityBoard.getCommunityId(),DBcommunity.getCommunityId());
        assertEquals(communityBoard.getTitle(),DBcommunity.getTitle());
        assertEquals(communityBoard.getContent(),DBcommunity.getContent());
        assertEquals(communityBoard.getDate(),DBcommunity.getDate());
        assertEquals(communityBoard.getAccount().getAccountId(),DBcommunity.getAccount().getAccountId());
    }

    @Test
    @DisplayName("커뮤니티 게시물을 삭제합니다.")
    void 게시물_삭제(){
        final Long communityId = 3L;
        Boolean resultCommunityDelete = communityServcie.delete(communityId);

        assertTrue(resultCommunityDelete);
    }
}
