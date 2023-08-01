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

    @GetMapping(value = "/list", params = {"page"})
    public List<BoardResForm> boardList(@RequestParam Integer page){
        log.info("boardList()");

        return  boardService.list(page);
    }
    @GetMapping(value = "/list/total-page")
    public Integer getTotalPage(){
        log.info("boardList()");

        return  boardService.getTotalPage();
    }

    @PostMapping("/register")
    public MemberBoard registerBoard(@RequestBody RequestRegisterBoardForm requestBoardForm) {
        log.info("registerBoard()");

        return boardService.register(requestBoardForm);
    }
    @GetMapping("/{boardId}")
    public BoardResForm readBoard(@PathVariable("boardId") Long boardId) {

        log.info("boardRead()");
        viewsCountUp(boardId);
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

    @GetMapping("/list/member/{page}")
    public List<BoardResForm> boardListWithMember(@RequestHeader HttpHeaders headers, @PathVariable("page") Integer page) {
        log.info("boardListWithMember()");
        return  boardService.listWithMember(headers, page);
    }
    @GetMapping(value = "/list/my-board-total-page")
    public Integer getMyBoardTotalPage(@RequestHeader HttpHeaders headers){
        log.info("boardList141414()");

        return  boardService.getMyBoardTotalPage(headers);
    }

    private void viewsCountUp(Long boardId){
        boardService.updateViews(boardId);
    }

}
