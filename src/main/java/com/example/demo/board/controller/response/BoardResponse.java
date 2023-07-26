package com.example.demo.board.controller.response;

import com.example.demo.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BoardResponse {

    private Long boardId;

    private String title;

    private String writer;

    private String content;

}
