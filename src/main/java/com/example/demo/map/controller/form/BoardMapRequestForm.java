package com.example.demo.map.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BoardMapRequestForm {

    final private String placeName;

    final private String title;

    private String writer;

    final private String content;

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
