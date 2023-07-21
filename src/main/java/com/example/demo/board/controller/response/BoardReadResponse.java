package com.example.demo.board.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BoardReadResponse {

    private Long boardiId;

    private String title;

    private String writer;

    private String content;

    private LocalDateTime createdData;
}
