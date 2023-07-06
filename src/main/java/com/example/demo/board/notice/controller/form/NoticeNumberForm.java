package com.example.demo.board.notice.controller.form;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeNumberForm {

    private Long noticeNumber;

    public NoticeNumberForm(Long noticeNumber) {
        this.noticeNumber = noticeNumber;
    }
}

