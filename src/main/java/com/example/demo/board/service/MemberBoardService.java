package com.example.demo.board.service;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.RequestRegisterBoardForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberBoardService {
    List<MemberBoard> list();
//    MemberBoard register(MemberBoard memberBoard);
    boolean register(RequestRegisterBoardForm requestform);

}
