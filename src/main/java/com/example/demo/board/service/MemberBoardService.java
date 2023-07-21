package com.example.demo.board.service;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.RequestModifyBoardForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.form.ResponseBoardForm;
import io.netty.handler.codec.Headers;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface MemberBoardService {
    List<MemberBoard> search(String keyword);

    List<MemberBoard> list();
    MemberBoard register(RequestRegisterBoardForm requestForm);

    ResponseBoardForm read(Long boardId);

    MemberBoard modify(RequestModifyBoardForm requestForm, Long boardId);

    boolean delete(Long boardId, HttpHeaders headers);
}
