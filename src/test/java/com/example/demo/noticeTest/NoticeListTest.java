package com.example.demo.noticeTest;

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
public class NoticeListTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지사항 게시판 목록 확인")
    void 공지사항_게시판_목록_확인 (){
        List<NoticeBoard> noticeBoardList = noticeService.list();

        for (NoticeBoard noticeBoard: noticeBoardList){
            System.out.println(noticeBoard.getNoticeNumber());
            System.out.println(noticeBoard.getNoticeTitle());
            System.out.println(noticeBoard.getNoticeDate());

            assertTrue(noticeBoard.getNoticeNumber() != null);
            assertTrue(noticeBoard.getNoticeTitle() != null);
            assertTrue(noticeBoard.getNoticeDate() != null);
        }
    }

    @Test
    @DisplayName("공지사항 게시물 상세 정보 확인")
    void 공지사항_게시물_정보_확인(){
        final Long noticeNumber = 8L;

        NoticeBoard noticeBoard = noticeService.read(noticeNumber);
        NoticeBoard dbNoticeBoard = noticeRepository.findByNoticeNumber(noticeNumber).get();

        System.out.println(noticeBoard);

        assertEquals(noticeBoard.getNoticeNumber(),dbNoticeBoard.getNoticeNumber());
        assertEquals(noticeBoard.getNoticeTitle(),dbNoticeBoard.getNoticeTitle());
        assertEquals(noticeBoard.getNoticeContent(),dbNoticeBoard.getNoticeContent());
        assertEquals(noticeBoard.getNoticeDate(),dbNoticeBoard.getNoticeDate());


    }
}
