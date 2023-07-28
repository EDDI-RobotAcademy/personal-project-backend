package com.example.demo.comment.service;

import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.comment.controller.form.RequestCommentForm;
import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
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
    final private BoardRepository boardRepository;
    final private UserRepository userRepository;
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
    public Comment modify(Long commentId, RequestCommentForm requestCommentForm) {
        return null;
    }

    @Override
    public Comment createComment(CommentDto commentDto) {
        String writer = commentDto.getWriter();
        String content = commentDto.getContent();
        Long boardId = commentDto.getBoardId();
        Long userId = commentDto.getUserId();

        Board board = boardRepository.findById(boardId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Comment comment = new Comment(writer, content);
        comment.setBoard(board);
        comment.setUser(user);

        return commentRepository.save(comment);
    }
}
