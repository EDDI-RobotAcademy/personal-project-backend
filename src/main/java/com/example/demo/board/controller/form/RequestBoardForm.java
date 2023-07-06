package com.example.demo.board.controller.form;


import com.example.demo.board.service.request.BoardRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RequestBoardForm {
    final private String title;
    final private String content;
    final private String writer;

    public BoardRegisterRequest toBoard() {
        return new BoardRegisterRequest(title, content, writer);
    }
}
