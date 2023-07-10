package com.example.demo.noticeTest;

import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.repository.NoticeRepository;
import com.example.demo.board.notice.service.NoticeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MockingNoticeTest {

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
    void 게시판_등록 (){
        final String title = "제목";
        final String content = "내용";
        final Long accountId = 1L;
        NoticeRegistForm noticeRegistForm = new NoticeRegistForm(title,content,accountId);
        final NoticeBoard noticeBoard = noticeRegistForm.toNoticeBoard();

        when(noticeRepository.save(noticeBoard)).thenReturn(new NoticeBoard("제목","내용",1L));
        final NoticeServiceImpl sut = new NoticeServiceImpl(noticeRepository);
        final NoticeBoard actual = sut.regist(noticeBoard);

        assertEquals(actual.getTitle(),noticeBoard.getTitle());
        assertEquals(actual.getContent(),noticeBoard.getContent());

    }

    @Test
    @DisplayName("공지사항 게시판 수정")
    void 게시판_수정(){

        final NoticeBoard noticeBoard = new NoticeBoard(1L,"제목","내용");
        final Long noticeId = 1L;
        final String title = "제목 변경";
        final String content = "내용 변경";
        NoticeModifyForm noticeModifyForm = new NoticeModifyForm(noticeId,title,content);
        when(noticeRepository.findByNoticeId(noticeId)).thenReturn(Optional.of(new NoticeBoard(1L,"제목","내용")));
        when(noticeRepository.save(any())).thenReturn(new NoticeBoard(1L,title,content));



        final NoticeBoard noticeBoard = noticeService.modify(noticeModifyForm.toNoticeBoard());


    }


}
