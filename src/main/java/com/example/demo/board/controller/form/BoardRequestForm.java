package com.example.demo.board.controller.form;

import com.example.demo.account.entity.Account;
import com.example.demo.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardRequestForm {

    final private String title;

    final private String writer;

    final private String content;

    final private Account account;

    public Board toBoard() {
        return new Board(title, writer, content, account);
    }
}
