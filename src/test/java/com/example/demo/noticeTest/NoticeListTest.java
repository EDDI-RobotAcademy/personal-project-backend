package com.example.demo.noticeTest;

import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NoticeListTest {

    @Autowired
    NoticeService noticeService;

    @Test
    @DisplayName("공지사항 게시판 목록 확인")
    void 공지사항_게시판_목록_확인 (){
        List<NoticeBoard> noticeBoardList = noticeService.list();

        for (NoticeBoard noticeBoard: noticeBoardList){
            System.out.println(noticeBoard.getId());
            System.out.println(noticeBoard.getNoticeTitle());
            System.out.println(noticeBoard.getNoticeContent());
            System.out.println(noticeBoard.getNoticeDate());

            assertTrue(noticeBoard.getId() != null);
            assertTrue(noticeBoard.getNoticeTitle() != null);
            assertTrue(noticeBoard.getNoticeContent() != null);
            assertTrue(noticeBoard.getNoticeDate() != null);
        }

    }
}
