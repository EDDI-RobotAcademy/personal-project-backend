package com.example.demo.comment.controller;
import com.example.demo.board.entity.Board;
import com.example.demo.board.service.request.BoardRegisterRequest;
import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.service.CommentService;
import com.example.demo.comment.service.Request.CommentRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    final private CommentService commentService;
    @GetMapping("/list/{boardId}")
    public List<Comment> commentList() {
        log.info("commentList()");

        List<Comment> returnedCommentList = commentService.list();
        return returnedCommentList;
    }
    @PostMapping("/register")
    public Comment registerComment (@RequestBody CommentRegisterRequest request) {
        log.info("registerComment()");
        return commentService.register(request.toComment());
    }
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        log.info("deleteComment()");
        commentService.delete(commentId);
    }
    @PostMapping("/new/register")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) {
        Comment createdComment = commentService.createComment(commentDto);
        return ResponseEntity.ok(createdComment);
    }
}
