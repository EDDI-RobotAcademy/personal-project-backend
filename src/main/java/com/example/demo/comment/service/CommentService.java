package com.example.demo.comment.service;
import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.entity.Board;
import com.example.demo.comment.controller.form.RequestCommentForm;
import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> list();
    Comment register(Comment comment);
    void delete(Long commentId);
    Comment modify(Long commentId, RequestCommentForm requestCommentForm);
    Comment createComment(CommentDto commentDto);

}
