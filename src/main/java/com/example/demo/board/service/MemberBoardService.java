package com.example.demo.board.service;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.BoardResForm;
import com.example.demo.board.form.RequestModifyBoardForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.form.ResponseBoardForm;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MemberBoardService {
    List<BoardResForm> search(String keyword);

    List<BoardResForm> list();
    List<BoardResForm> list(@RequestParam(value="page", required=false)Integer page);
    List<BoardResForm> listWithMember(HttpHeaders headers, int page);
    Integer getMyBoardTotalPage(HttpHeaders headers);

    MemberBoard register(RequestRegisterBoardForm requestForm);

    BoardResForm read(Long boardId);

    BoardResForm modify(RequestModifyBoardForm requestForm, Long boardId);

    boolean delete(Long boardId, HttpHeaders headers);

    Integer getTotalPage();

    Integer updateViews(Long boardId);
}
