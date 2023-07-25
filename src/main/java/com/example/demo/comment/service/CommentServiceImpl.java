package com.example.demo.comment.service;

import com.example.demo.board.entity.Board;
import com.example.demo.comment.controller.form.RequestCommentForm;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    final private CommentRepository commentRepository;
    @Override
    public List<Comment> list() {
        return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "commentId"));
    }
    @Override
    public Comment register(Comment comment) {
        return commentRepository.save(comment);
    }
    @Override
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    @Override
    public Board modify(Long commentId, RequestCommentForm requestCommentForm) {
        return null;
    }
}
