package kr.eddi.demo.comment.service;


import kr.eddi.demo.comment.controller.form.CommentRegisterForm;
import kr.eddi.demo.comment.entity.Comment;

import kr.eddi.demo.comment.entity.ReportedComment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentService {


    Boolean register(CommentRegisterForm form);
    @Transactional
    List<Comment> list(Long boardId);

    void delete(Long id);
    public void reportComment(Long commentId);

    List<ReportedComment> getAllReportedComments();
}
