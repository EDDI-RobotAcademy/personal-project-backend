package com.example.demo.board.notice.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class NoticeListForm {
    final private Long noticeId;
    final private String title;
    final private LocalDateTime date;

}
