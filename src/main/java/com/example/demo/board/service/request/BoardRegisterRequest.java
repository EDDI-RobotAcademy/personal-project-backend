package com.example.demo.board.service.request;

import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class BoardRegisterRequest {
    final private String writer;
    final private String title;
    final private String content;
    final private BoardCategory category;



    public Board toBoard() {
        return new Board(writer, title, content, category);
    }
}