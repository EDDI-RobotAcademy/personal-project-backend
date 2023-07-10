package com.example.demo.noticeTest;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.repository.NoticeRepository;
import com.example.demo.board.notice.service.NoticeService;
import com.example.demo.board.notice.service.NoticeServiceImpl;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoticeTest {

    @Mock
    private NoticeRepository noticeRepository;
    @InjectMocks
    NoticeServiceImpl noticeService;

    @BeforeEach
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("공지사항 게시판 등록")
    void 공지사항_게시판_등록 (){
        final String title = "제목9";
        final String content = "내용9";
        NoticeRegistForm noticeRegistForm = new NoticeRegistForm(title,content);
        final NoticeBoard noticeBoard = noticeRegistForm.toNoticeBoard();

        when(noticeRepository.save(noticeBoard)).thenReturn(new NoticeBoard("제목9","내용9"));
        final NoticeServiceImpl sut = new NoticeServiceImpl(noticeRepository);
        final NoticeBoard actual = sut.regist(noticeBoard);

        assertEquals(actual.getTitle(),noticeBoard.getTitle());
        assertEquals(actual.getContent(),noticeBoard.getContent());

    }
}
