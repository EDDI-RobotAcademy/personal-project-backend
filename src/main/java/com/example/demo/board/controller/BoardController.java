package com.example.demo.board.controller;

import org.springframework.ui.Model;
import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
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
    final private BoardRepository boardRepository;
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
    @GetMapping("/{boardId}")
    public Board readBoard(@PathVariable("boardId")Long boardId){
        log.info("boardRead()");
        return boardService.read(boardId);
    }

//    @PutMapping("/read-count/{boardId}")
//    public void updateReadCount(@PathVariable("boardId") Long boardId) {
//        log.info("updateReadCount()");
//        boardService.updateReadCount();
//    }

    @GetMapping("/read-count/{boardId}")
    public String boardContent(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
        Integer readCount = board.getReadCount() + 1;

        boardService.updateReadCount(boardId, readCount);

        model.addAttribute("board", board);
        return "redirect:/key-we-board-page/read/" + boardId;
    }


    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        log.info("modifyBoard()");
        boardService.delete(boardId);
    }

    @PutMapping("/{boardId}")
    public Board modifyBoard(@PathVariable("boardId")Long boardId, @RequestBody RequestBoardForm requestBoardForm) {
        log.info("modifyBoard(): " + requestBoardForm + ", id" + boardId);
        return boardService.modify(boardId, requestBoardForm);
    }
    @GetMapping("/search")
    public List<Board> searchBoard(@RequestParam("boardId") String keyword) {
        List<Board> searchBoardList = boardService.search(keyword);
        return searchBoardList;
    }
}
