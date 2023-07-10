package com.example.demo.noticeTest;

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
public class NoticeTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지사항 게시물 생성")
    void 게시물_생성(){
        final String title = "공지사항 제목8";
        final String content = "공지사항 내용8";
        final Long accountId = 1L;
        NoticeRegistForm noticeRegistForm = new NoticeRegistForm(title,content,accountId);
        NoticeBoard noticeBoard = noticeService.regist(noticeRegistForm.toNoticeBoard());
        long noticeId = noticeBoard.getNoticeId();
        NoticeBoard DBnotice = noticeRepository.findByNoticeId(noticeId).get();

        assertEquals(title,DBnotice.getTitle());
        assertEquals(content,DBnotice.getContent());
        assertEquals(accountId,DBnotice.getAccount().getAccountId());

    }

    @Test
    @DisplayName("공지사항 게시물을 수정합니다.")
    void 게시물_수정(){
        final Long noticeId = 3L;
        final String title = "변경 제목1";
        final String content = "변경 내용1";
        NoticeModifyForm noticeModifyForm = new NoticeModifyForm(noticeId,title,content);
        NoticeBoard noticeBoard = noticeModifyForm.toNoticeBoard();
        noticeService.modify(noticeBoard);
        NoticeBoard DBnotice = noticeRepository.findByNoticeId(noticeId).get();

        assertEquals(title,DBnotice.getTitle());
        assertEquals(content,DBnotice.getContent());
    }

    @Test
    @DisplayName("공지사항 게시판 목록 확인")
    void 게시판_목록_확인 (){
        List<NoticeBoard> noticeBoardList = noticeService.list();

        for (NoticeBoard noticeBoard: noticeBoardList){
            System.out.println("===============");
            System.out.println(noticeBoard.getNoticeId());
            System.out.println(noticeBoard.getTitle());
            System.out.println(noticeBoard.getDate());
            System.out.println(noticeBoard.getAccount().getAccountId());

            assertTrue(noticeBoard.getNoticeId() != null);
            assertTrue(noticeBoard.getTitle() != null);
            assertTrue(noticeBoard.getDate() != null);
            assertTrue(noticeBoard.getAccount().getAccountId() != null);
        }
    }

    @Test
    @DisplayName("공지사항 게시물 상세 정보 확인")
    void 게시물_정보_확인(){
        final Long noticeId = 3L;
        NoticeBoard noticeBoard = noticeService.read(noticeId);
        NoticeBoard DBnotice = noticeRepository.findByNoticeId(noticeId).get();
        System.out.println(noticeBoard.getNoticeId());
        System.out.println(noticeBoard.getTitle());
        System.out.println(noticeBoard.getContent());
        System.out.println(noticeBoard.getAccount().getAccountId());

        assertEquals(noticeBoard.getNoticeId(),DBnotice.getNoticeId());
        assertEquals(noticeBoard.getTitle(),DBnotice.getTitle());
        assertEquals(noticeBoard.getContent(),DBnotice.getContent());
        assertEquals(noticeBoard.getDate(),DBnotice.getDate());
        assertEquals(noticeBoard.getAccount().getAccountId(),DBnotice.getAccount().getAccountId());
    }

    @Test
    @DisplayName("공지사항 게시물을 삭제합니다.")
    void 게시물_삭제(){
        final Long noticeId = 3L;
        Boolean resultNoticeDelete = noticeService.delete(noticeId);

        assertTrue(resultNoticeDelete);
    }
}
