package com.example.demo.board.service;

import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.service.request.BoardRegisterRequest;
import jakarta.transaction.Transactional;

import java.util.List;


public interface BoardService {
    List<Board> list();

    Board register(Board registerBoard);

//    @Transactional
//    public int updateReadCount(Long boardId);
    Board read(Long boardId);
    void delete(Long boardId);
    Board modify(Long boardId, RequestBoardForm requestBoardForm);

}
