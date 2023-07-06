package com.example.demo.noticeTest;

import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.repository.NoticeRepository;
import com.example.demo.board.notice.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoticeDeleteTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지사항 게시물을 삭제합니다.")
    void 게시물_삭제(){
        final Long noticeNumber = 7L;

        Boolean resultNoticeDelete = noticeService.delete(noticeNumber);

        assertTrue(resultNoticeDelete);
    }
}
