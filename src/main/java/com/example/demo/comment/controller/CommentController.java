package com.example.demo.comment.controller;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.BoardResForm;
import com.example.demo.board.form.CommentResForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.comment.form.RequestRegisterCommentForm;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board/comment")
public class CommentController {

    final private CommentService commentService;

    @PostMapping("/{boardId}/register")
    public BoardResForm registerBoard(@RequestBody RequestRegisterCommentForm requestCommentForm, @PathVariable Long boardId) {
        log.info("registerComment()");
        return commentService.register(requestCommentForm, boardId);
    }

    @PostMapping("/{commentId}/modify")
    public CommentResForm modifyBoard(@RequestBody RequestRegisterCommentForm requestCommentForm, @PathVariable Long commentId) {
        log.info("modifyComment()");
        return commentService.modify(requestCommentForm, commentId);
    }
    @DeleteMapping("/{commentId}")
    public Boolean deleteBoard (@PathVariable("commentId") Long commentId, @RequestHeader HttpHeaders headers) {
        log.info("deleteBoard() ");
        return commentService.delete(commentId, headers);
    }
}
