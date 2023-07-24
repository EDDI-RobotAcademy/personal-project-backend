package com.example.demo.map.controller.response;

import com.example.demo.map.entity.BoardMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class BoardMapListResponse {

    private Long boardMapId;

    private String placeName;

    private String title;

    private String writer;

    private LocalDateTime createdData;

    public void setBoardMap(BoardMap boardMap) {
        this.placeName = placeName;
        this.title = title;
        this.writer = writer;
        this.createdData = createdData;
    }
}
