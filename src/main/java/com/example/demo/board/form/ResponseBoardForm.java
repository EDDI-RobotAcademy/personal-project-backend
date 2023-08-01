package com.example.demo.board.form;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.comment.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class ResponseBoardForm {
    final private MemberBoard memberBoard;
    final private List<FilePaths> filePathList;
//    final private List<Comment> commentList;

}
