package com.example.demo.board.service;

import com.example.demo.board.controller.form.BoardCategoryListForm;
import com.example.demo.board.controller.form.BoardCategoryResponseForm;
import com.example.demo.board.controller.form.BoardResponseForm;
import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.dto.BoardResponseDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
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
    void updateReadCount(Long boardId,Integer readCount);
    void delete(Long boardId);
    Board modify(Long boardId, RequestBoardForm requestBoardForm);
    List<Board> search(String keyword);
    List<BoardCategoryListForm> getCategoryList();
    List<BoardCategoryResponseForm> getListByCategory(BoardCategory category);
    BoardResponseForm getBoardsByPage(int pageNumber, int pageSize);

//    Page<Board>boardList(Pageable pageable);
//     Page<Board> boardSearchList(String searchKeyword, Pageable pageable);
}
