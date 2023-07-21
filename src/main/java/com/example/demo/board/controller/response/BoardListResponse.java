package com.example.demo.board.controller.response;

import com.example.demo.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class BoardListResponse {

    private Long boardId;

    private String title;

    private String writer;

    private LocalDateTime createdData;

    public void setBoard(Board board) {
        this.title = title;
        this.writer = writer;
        this.createdData = createdData;
    }
}
