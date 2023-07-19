package com.example.demo.board.controller;

import com.example.demo.board.controller.form.BoardRequestForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    final private BoardService boardService;

    @GetMapping("/list")
    public List<Board> boardList () {
        log.info("boardList");

        return boardService.list();
    }

    @PostMapping("/register")
    public Board registerBoard(@RequestBody BoardRequestForm form, @RequestHeader("Authorization") String accessToken) {
        log.info("form: " + form);
        log.info("accessToken: " + accessToken);

        return boardService.register(accessToken, form.toBoard());
    }
}
