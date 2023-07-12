package com.example.demo.noticeTest;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.service.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MockingNoticeTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NoticeService noticeService;

    @Test
    @DisplayName("공지사항 게시판 등록")
    public void 게시판_등록 () throws Exception {
        final String title = "제목";
        final String content = "내용";
        final Long accountId = 1L;
        final NoticeRegistForm noticeRegistForm = new NoticeRegistForm(title,content,accountId);
        final NoticeBoard noticeBoard = noticeRegistForm.toNoticeBoard();
        ObjectMapper objectMapper = new ObjectMapper();
        String boardRequestBody = objectMapper.writeValueAsString(noticeRegistForm);
        when(noticeService.regist(noticeRegistForm.toNoticeBoard())).thenReturn(noticeBoard);

        mockMvc.perform(post("/notice/regist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"));

        verify(noticeService, times(1)).regist(noticeRegistForm.toNoticeBoard());
    }

    @Test
    @DisplayName("공지사항 게시판 수정")
    void 게시판_수정(){

//        final NoticeBoard noticeBoard = new NoticeBoard(1L,"제목","내용");
//        final Long noticeId = 1L;
//        final String title = "제목 변경";
//        final String content = "내용 변경";
//        NoticeModifyForm noticeModifyForm = new NoticeModifyForm(noticeId,title,content);
//        when(noticeRepository.findByNoticeId(noticeId)).thenReturn(Optional.of(new NoticeBoard(1L,"제목","내용")));
//        when(noticeRepository.save(any())).thenReturn(new NoticeBoard(1L,title,content));
//
//
//
//        final NoticeBoard noticeBoard = noticeService.modify(noticeModifyForm.toNoticeBoard());


    }


}
