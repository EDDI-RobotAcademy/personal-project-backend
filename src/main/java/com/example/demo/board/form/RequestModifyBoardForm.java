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
public class RequestModifyBoardForm {

//    final private Long boardId;
    final private String title;
    final private String nickName;
    final private String content;
    final private List<FilePaths> awsFileList;

    public MemberBoard toMemberBoard() {
        return new MemberBoard(title, nickName, content, awsFileList);
    }
}
