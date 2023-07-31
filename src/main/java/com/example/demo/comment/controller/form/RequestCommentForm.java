package com.example.demo.comment.controller.form;


import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.service.request.BoardRegisterRequest;
import com.example.demo.comment.service.Request.CommentRegisterRequest;
import com.example.demo.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RequestCommentForm {
    final private String writer;
    final private String content;

    public CommentRegisterRequest toComment() {
        return new CommentRegisterRequest(writer,content);
    }
}
