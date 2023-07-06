package com.example.demo.board.service;

import com.example.demo.board.entity.Board;
import com.example.demo.board.service.request.BoardRegisterRequest;

import java.util.List;

public interface BoardService {
    List<Board> list();

Board register(Board registerBoard);
}
