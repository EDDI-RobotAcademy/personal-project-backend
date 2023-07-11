package com.example.demo.board.event.controller.form;

import com.example.demo.board.event.entity.EventBoard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventRegistForm {
    final private String title;
    final private String content;
    final private Long accountId;
    final private String image;

    public EventBoard toEventBoard() {
        return new EventBoard(title, content,accountId,image);
    }
}
