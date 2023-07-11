package com.example.demo.board.notice.controller.form;

import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class NoticeRegistForm {
    final private String title;
    final private String content;
    final private Long accountId;

    public NoticeBoard toNoticeBoard() {
        return new NoticeBoard(title, content,accountId);
    }
}
