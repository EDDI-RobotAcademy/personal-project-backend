package com.example.demo.board.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public class BoardCategoryResponseForm {
    final private Long boardId;
    final private String title;
    final private String writer;
    final private String content;
//    final private Data createDate;

}
