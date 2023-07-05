package com.example.demo.noticeTest;

import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.repository.NoticeRepository;
import com.example.demo.board.notice.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoticeModifyTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지사항 게시물을 수정합니다.")
    void 공지사항_수정(){

        final Long noticeNumber = 3L;
        final String noticeTitle = "변경 제목1";
        final String noticeContent = "변경 내용1";

        NoticeModifyForm noticeModifyForm = new NoticeModifyForm(noticeNumber,noticeTitle,noticeContent);

        noticeService.modify(noticeModifyForm);
        NoticeBoard dbnotice = noticeRepository.findByNoticeNumber(noticeNumber).get();

        assertEquals(noticeModifyForm.getNoticeTitle(),dbnotice.getNoticeTitle());
        assertEquals(noticeModifyForm.getNoticeContent(),dbnotice.getNoticeContent());

    }
}
