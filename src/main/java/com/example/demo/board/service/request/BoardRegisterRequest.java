package com.example.demo.board.service.request;

import com.example.demo.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BoardRegisterRequest {
    final private String title;
    final private String writer;
    final private String content;

    public Board toBoard(){
        return new Board(title, writer, content);
    }
}
