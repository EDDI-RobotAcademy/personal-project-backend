package com.example.demo.map.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BoardMapRegisterResponse {

    private Long boardMapId;

    private String title;

    private String writer;

    private String content;
}
