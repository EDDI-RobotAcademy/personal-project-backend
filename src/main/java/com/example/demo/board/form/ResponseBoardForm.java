package com.example.demo.board.form;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class ResponseBoardForm {
    private MemberBoard memberBoard;
    private List<FilePaths> filePathList;

    public ResponseBoardForm(MemberBoard memberBoard, List<FilePaths> savedFilePath) {
        this.memberBoard = memberBoard;
        this.filePathList =savedFilePath;

    }
}
