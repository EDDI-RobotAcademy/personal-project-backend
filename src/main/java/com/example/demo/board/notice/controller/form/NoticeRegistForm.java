package com.example.demo.board.notice.controller.form;

import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeRegistForm {
    String noticeTitle;
    String noticeContent;

    public NoticeBoard toNoticeBoard() {
        return new NoticeBoard(noticeTitle, noticeContent);
    }
}
