package com.example.demo.communityTest;

import com.example.demo.board.community.controller.form.CommunityRegistForm;
import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.community.repository.CommunityRepository;
import com.example.demo.board.community.service.CommunityServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CommunityTest {

    @Mock
    CommunityRepository communityRepository;

    @InjectMocks
    CommunityServiceImpl communityService;

    @Test
    @DisplayName("커뮤니티 게시물 생성")
    void 게시물_생성(){
        final CommunityRegistForm communityRegistForm = new CommunityRegistForm("제목입니다.","내용입니다.");
        final CommunityBoard communityBoard = communityRegistForm.toCommunityBoard();
        when(communityRepository.save(communityBoard)).thenReturn(new CommunityBoard("제목입니다.","내용입니다."));

        final CommunityServiceImpl sut = new CommunityServiceImpl(communityRepository);
        final CommunityBoard actual = sut.regist(communityBoard);

        assertEquals(actual.getCommunityNumber(),communityBoard.getCommunityNumber());
        assertEquals(actual.getCommunityTitle(),communityBoard.getCommunityTitle());
        assertEquals(actual.getCommunityContent(),communityBoard.getCommunityContent());
        assertEquals(actual.getCommunityDate(),communityBoard.getCommunityDate());



    }



}
