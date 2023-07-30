package com.example.demo.board.controller;

import com.example.demo.board.controller.form.BoardCategoryListForm;
import com.example.demo.board.controller.form.BoardCategoryResponseForm;
import com.example.demo.board.controller.form.BoardResponseForm;
import com.example.demo.board.dto.LikeCountRequestDto;
import com.example.demo.board.entity.BoardCategory;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.service.BoardService;
import com.example.demo.board.service.request.BoardRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
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
        log.info("readBoard()");
        return boardService.read(boardId);
    }

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
        log.info("searchBoard()");
        return searchBoardList;
    }
    @GetMapping("/category")
    public List<BoardCategoryListForm> getCategoryList() {
        return boardService.getCategoryList();
    }
    @GetMapping("list/{category}")
    public List<BoardCategoryResponseForm> categoryResponseForms (@PathVariable("category")BoardCategory category){
        return boardService.getListByCategory(category);
    }
    @GetMapping("/list-page")
    public ResponseEntity<BoardResponseForm> getBoardsByPage(@RequestParam("page") int pageNumber, @RequestParam("size") int pageSize) {
        BoardResponseForm boardResponse = boardService.getBoardsByPage(pageNumber, pageSize);
        return ResponseEntity.ok(boardResponse);
    }
    @PostMapping("/like-count/{boardId}")
    public ResponseEntity<?> addLikeCount(@PathVariable Long boardId, @RequestBody LikeCountRequestDto requestDto) {
        try {
            boardService.addLikeCount(boardId, requestDto.getUserId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to increase like count.");
        }
    }
    @GetMapping("/like-count/{boardId}")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long boardId) {
        int likeCount = boardService.getLikeCount(boardId);
        return ResponseEntity.ok(likeCount);
    }

}
