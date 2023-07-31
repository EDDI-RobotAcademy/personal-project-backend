package com.example.demo.board.controller.form;

import com.example.demo.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
@Setter
@RequiredArgsConstructor
public class BoardResponseForm {
    private List<Board> data;
    private int totalPages;
    private int currentPage;
}
