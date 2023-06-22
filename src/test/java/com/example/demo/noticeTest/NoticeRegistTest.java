package com.example.demo.noticeTest;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.service.NoticeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class NoticeRegistTest {

    @Autowired
    NoticeService noticeService;


    @Test
    @DisplayName("공지사항 게시판 등록")
    void 공지사항_게시판_등록 (){
        final String noticeTitle = "공지사항 제목2";
        final String noticeContent = "공지사항 내용2";

        NoticeRegistForm noticeRegistForm = new NoticeRegistForm(noticeTitle,noticeContent);

        NoticeBoard noticeBoard = noticeService.regist(noticeRegistForm);

        Assertions.assertEquals(noticeTitle, noticeBoard.getNoticeTitle());

    }
}
