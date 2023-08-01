package com.example.demo.board.notice.controller.form;

import com.example.demo.board.notice.entity.NoticeBoard;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Getter
@NoArgsConstructor
public class NoticeRegistForm {
    private String title;
    private String content;
//    final private Long accountId;

//    public NoticeBoard toNoticeBoard() {
//        return new NoticeBoard(title, content,accountId);
//    }


    public NoticeRegistForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoticeBoard toNoticeBoard() {
        return new NoticeBoard(title, content);
    }
}
