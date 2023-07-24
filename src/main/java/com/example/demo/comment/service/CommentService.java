package com.example.demo.comment.service;

import com.example.demo.board.form.BoardResForm;
import com.example.demo.board.form.CommentResForm;
import com.example.demo.comment.form.RequestRegisterCommentForm;

public interface CommentService {

    BoardResForm register(RequestRegisterCommentForm requestCommentForm, Long boardId);

    CommentResForm modify(RequestRegisterCommentForm requestCommentForm, Long commentId);
}
