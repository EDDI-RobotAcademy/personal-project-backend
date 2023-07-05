package com.example.demo.board.notice.controller;

import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeNumberForm;
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
    public String noticeRegist(NoticeRegistForm noticeRegistForm){
        log.info("NoticeRegist run");
        NoticeBoard noticeBoard = noticeService.regist(noticeRegistForm);

        if (noticeBoard == null){
            return null;
        }
        return noticeBoard.getNoticeTitle();
    }

    // 공지사항 게시물 목록 확인 기능
    @GetMapping("/list")
    public List<NoticeBoard> noticeList(){
        log.info("NoticeList run");
        List<NoticeBoard> returnedNoticeList = noticeService.list();
        log.info("NoticeList : " + returnedNoticeList);

        return returnedNoticeList;
    }

    @PutMapping("/modify")
    public void noticeModify(@RequestBody NoticeModifyForm noticeModifyForm){
        log.info("noticeModify() ");
        NoticeBoard noticeBoard = noticeService.modify(noticeModifyForm);

    }


}
