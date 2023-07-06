package com.example.demo.board.controller;

import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.service.BoardService;
import com.example.demo.board.service.request.BoardRegisterRequest;
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
    public List<Board> boardList() {
        log.info("boardList()");

        List<Board> returnedBoardList = boardService.list();
        return returnedBoardList;
    }
    @PostMapping("/register")
    public Board registerBoard (@RequestBody BoardRegisterRequest request) {
        return boardService.register(request.toBoard());
    }
}
