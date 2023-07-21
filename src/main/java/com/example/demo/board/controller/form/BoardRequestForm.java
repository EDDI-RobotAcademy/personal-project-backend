package com.example.demo.board.controller.form;

import com.example.demo.account.entity.Account;
import com.example.demo.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BoardRequestForm {

    final private String title;

    private String writer;

    final private String content;

    public Board toBoard() {
        return new Board(title, writer, content);
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
