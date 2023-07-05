package com.example.demo.board.notice.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeModifyForm {

    final private Long noticeNumber;
    final private String noticeTitle;
    final private String noticeContent;
}
