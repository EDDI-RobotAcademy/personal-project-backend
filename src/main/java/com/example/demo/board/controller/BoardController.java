package com.example.demo.board.controller;

import com.example.demo.board.controller.form.BoardRequestForm;
import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.board.controller.response.BoardReadResponse;
import com.example.demo.board.controller.response.BoardResponse;
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
    public List<BoardListResponse> boardList () {
        return boardService.list();
    }

    @PostMapping("/register")
    public BoardResponse registerBoard(@RequestBody BoardRequestForm form, @RequestHeader("Authorization") String accessToken) {

        return boardService.register(accessToken, form);
    }

    @GetMapping("/{boardId}")
    public BoardReadResponse read(@PathVariable("boardId") Long boardId) {
        return boardService.read(boardId);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable("boardId") Long boardId,@RequestHeader("Authorization") String accessToken) {
        boardService.delete(boardId, accessToken);
    }

    @PutMapping("/{boardId}")
    public BoardResponse modify(@PathVariable("boardId") Long boardId, @RequestBody BoardRequestForm form, @RequestHeader("Authorization") String accessToken) {
        return boardService.modify(boardId, form, accessToken);
    }
}
