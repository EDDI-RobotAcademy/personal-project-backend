package com.example.demo.board.service;

import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.dto.BoardResponseDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.service.request.BoardRegisterRequest;
import com.example.demo.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BoardService {
    List<Board> list();

    Board register(Board registerBoard);

    Board read(Long boardId);
    void delete(Long boardId);
    Board modify(Long boardId, RequestBoardForm requestBoardForm);


//    BoardResponseDto findByBoardId(Long boardId);
    Board findById(Long boardId);

    Page<Board>boardList(Pageable pageable);
     Page<Board> boardSearchList(String searchKeyword, Pageable pageable);
}
