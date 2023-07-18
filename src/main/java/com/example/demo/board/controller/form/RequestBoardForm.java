package com.example.demo.board.controller.form;


import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.service.request.BoardRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RequestBoardForm {
    final private String writer;
    final private String title;
    final private String content;
    final private Long category;

    public BoardRegisterRequest toBoard() {
        return new BoardRegisterRequest(writer, title, content, category);
    }
}
