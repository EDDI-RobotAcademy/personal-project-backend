package com.example.demo.board.event.controller.form;

import com.example.demo.board.event.entity.EventBoard;
import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EventModifyForm {

    final private Long eventId;
    final private String title;
    final private String content;
    final private String image;

    public EventBoard toEventBoard() {
        return new EventBoard(eventId,title,content,image);
    }
}
