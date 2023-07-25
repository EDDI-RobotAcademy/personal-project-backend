package com.example.demo.comment.service.Request;

import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.comment.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRegisterRequest {
    final private String writer;
    final private String content;
    public Comment toComment() {
        return new Comment(writer, content);
    }
}