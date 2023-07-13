package com.example.demo.board.service;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.form.ResponseBoardForm;

import java.util.List;

public interface MemberBoardService {
    List<MemberBoard> list();
    MemberBoard register(RequestRegisterBoardForm requestForm);

    ResponseBoardForm read(Long boardId);

}
