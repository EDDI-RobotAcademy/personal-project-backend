package com.example.demo.board.form;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member-board")
public class MemberBoardController {

    final private MemberBoardService boardService;

    @GetMapping("/list")
    public List<BoardResForm> boardList() {
        log.info("boardList()");

        return  boardService.list();
    }

    @PostMapping("/register")
    public MemberBoard registerBoard(@RequestBody RequestRegisterBoardForm requestBoardForm) {
        log.info("registerBoard()");

        return boardService.register(requestBoardForm);
    }
    @GetMapping("/{boardId}")
    public BoardResForm readBoard(@PathVariable("boardId") Long boardId) {

        log.info("boardRead()");

        return boardService.read(boardId);
    }

    @GetMapping("/search")
      public List<BoardResForm> findBoardList(@RequestParam("keyword") String keyword){

        return boardService.search(keyword);
    }
    @PutMapping("/{boardId}")
    public BoardResForm modifyBoard (@PathVariable("boardId") Long boardId,
                                 @RequestBody RequestModifyBoardForm requestBoardForm) {
        log.info("modifyBoard(): " + requestBoardForm + ", id: " + boardId);

        return boardService.modify(requestBoardForm, boardId);
    }

    @DeleteMapping("/{boardId}")
    public Boolean deleteBoard (@PathVariable("boardId") Long boardId, @RequestHeader HttpHeaders headers){
        log.info("deleteBoard() id: " + boardId);
        return boardService.delete(boardId, headers);


    }


}
