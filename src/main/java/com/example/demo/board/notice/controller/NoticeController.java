package com.example.demo.board.notice.controller;

import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    final NoticeService noticeService;

    // 공지사항 게시물 등록 기능
    @PostMapping("/regist")
    public String noticeRegist(@RequestBody NoticeRegistForm noticeRegistForm){
        log.info("NoticeRegist() ");
        NoticeBoard noticeBoard = noticeService.regist(noticeRegistForm.toNoticeBoard());
        if (noticeBoard == null){
            return null;
        }
        return noticeBoard.getTitle();
    }

    // 공지사항 게시판 수정
    @PutMapping("/{noticeId}")
    public NoticeBoard noticeModify(@RequestBody NoticeModifyForm noticeModifyForm){
        log.info("NoticeModify() ");
        NoticeBoard noticeBoard = noticeService.modify(noticeModifyForm.toNoticeBoard());

        return noticeBoard;
    }

    // 공지사항 게시물 목록 확인
    @GetMapping("/list")
    public List<NoticeBoard> noticeList(){
        log.info("NoticeList() ");
        List<NoticeBoard> returnedNoticeList = noticeService.list();
        log.info("NoticeList : " + returnedNoticeList);

        return returnedNoticeList;
    }

    // 공지사항 게시물 읽기
    @GetMapping("/{noticeId}")
    public NoticeBoard noticeRead(@PathVariable String noticeId){
        log.info("NoticeRead() ");
        NoticeBoard readNoticeBoard = noticeService.read(noticeId);

        return readNoticeBoard;
    }


    // 공지사항 게시물 삭제
    @DeleteMapping("/{noticeId}")
    public boolean noticeDelete(@RequestParam("noticeId") String noticeId){
        log.info("NoticeDelete() ");
        boolean resultDeleteNotice = noticeService.delete(noticeId);

        return resultDeleteNotice;
    }

}
