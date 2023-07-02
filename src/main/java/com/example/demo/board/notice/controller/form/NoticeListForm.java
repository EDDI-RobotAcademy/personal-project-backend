package com.example.demo.board.notice.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class NoticeListForm {
    final private Long noticeNumber;
    final private String noticeTitle;
    final private String noticeContent;
    final private LocalDateTime noticeDate;

}
