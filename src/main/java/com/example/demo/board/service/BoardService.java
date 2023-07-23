package com.example.demo.board.service;

import com.example.demo.board.controller.form.BoardRequestForm;
import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.board.controller.response.BoardReadResponse;
import com.example.demo.board.controller.response.BoardResponse;
import com.example.demo.board.entity.Board;

import java.util.List;

public interface BoardService {
    List<BoardListResponse> list();

    BoardResponse register(String accessToken, BoardRequestForm form);

    BoardReadResponse read(Long boardId, String accessToken);

    void delete(Long boardId, String accessToken);

    BoardResponse modify(Long boardId, BoardRequestForm form, String accessToken);
}
