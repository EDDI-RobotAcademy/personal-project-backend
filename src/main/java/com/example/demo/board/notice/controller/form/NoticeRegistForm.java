package com.example.demo.board.notice.controller.form;

import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;

public class NoticeRegistForm {
    String noticeTitle;
    String noticeContent;

    public NoticeRegistForm(String noticeTitle, String noticeContent) {
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
    }

    public NoticeBoard toNoticeBoard() {
        return new NoticeBoard(noticeTitle, noticeContent);
    }
}
