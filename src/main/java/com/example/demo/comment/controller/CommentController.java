package com.example.demo.comment.controller;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.BoardResForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.comment.form.RequestRegisterCommentForm;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


}
