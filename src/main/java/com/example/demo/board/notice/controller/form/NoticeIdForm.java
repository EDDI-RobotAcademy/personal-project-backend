package com.example.demo.board.notice.controller.form;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeIdForm {

    private Long noticeId;

    public NoticeIdForm(Long noticeId) {
        this.noticeId = noticeId;
    }
}

