package com.example.demo.board.notice.controller;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class noticeController {

    final NoticeService noticeService;

    @PostMapping("/regist")
    public String noticeRegist(NoticeRegistForm noticeRegistForm){
        log.info("NoticeRegist run");
        NoticeBoard noticeBoard = noticeService.regist(noticeRegistForm);

        if (noticeBoard == null){
            return null;
        }
        return noticeBoard.getNoticeTitle();

    }
}
