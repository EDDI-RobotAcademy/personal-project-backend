package com.example.demo.board.notice.controller.form;

import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeRegistForm {
    final private String noticeTitle;
    final private String noticeContent;


    public NoticeBoard toNoticeBoard() {
        return new NoticeBoard(noticeTitle, noticeContent);
    }
}
