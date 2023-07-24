package kr.eddi.demo.board.controller;

import kr.eddi.demo.account.controller.form.UsertokenRequestForm;
import kr.eddi.demo.board.controller.form.BoardModifyRequest;
import kr.eddi.demo.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.board.entity.Board;
import kr.eddi.demo.board.service.BoardService;
import kr.eddi.demo.board.service.reqeust.BoardRegisterRequest;
import kr.eddi.demo.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardController {

    @Autowired
    final private BoardService boardService;
    @Autowired
    final private RedisService redisService;
    @PostMapping("/register")
    public Board boardRegister(@RequestBody BoardRegisterRequestForm form){
      Board registerBoard= boardService.boardRegister(form);
      log.info(registerBoard.getImgPath());

    return registerBoard;
    }
    @PostMapping("/list")
    public List<Board> boardList(){
        List<Board> returnBoardList= boardService.list();
        log.info(returnBoardList.toString());
        return returnBoardList;
    }
    @PostMapping("/read/{id}")
    public Board readBoard (@PathVariable("id") Long id){
        log.info("보드 가져와");
        return boardService.read(id);
    }
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Long id){
        boardService.delete(id);
    }

    @PutMapping("/{id}")
    public Board modifyBoard (@PathVariable("id") Long id,
                              @RequestBody BoardModifyRequest request){

        log.info(request.toString());
        return boardService.modify(id, request);
    }
    @PostMapping("/myList") List<Board> myBoardList (@RequestBody UsertokenRequestForm usertokenRequestForm){
        log.info("이제 찾을거야"+usertokenRequestForm.getUserToken());
        return boardService.myBoards(usertokenRequestForm.getUserToken());
    }
}
