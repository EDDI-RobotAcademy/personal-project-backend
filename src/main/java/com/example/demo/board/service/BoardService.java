package com.example.demo.board.service;

import com.example.demo.board.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> list();

    Board register(String accessToken, Board board);

    Board read(Long boardId);
}
