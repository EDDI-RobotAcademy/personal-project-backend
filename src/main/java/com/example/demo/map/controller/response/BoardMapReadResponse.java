package com.example.demo.map.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BoardMapReadResponse {

    private Long accountId;

    private Long boardMapId;

    private String placeName;

    private String title;

    private String writer;

    private String content;

    private LocalDateTime createdData;
}
