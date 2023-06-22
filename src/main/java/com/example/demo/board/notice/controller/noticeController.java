package com.example.demo.board.notice.controller;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice")
public class noticeController {
    @PostMapping("/regist")
    public void noticeRegist(NoticeRegistForm noticeRegistForm){

    }
}
