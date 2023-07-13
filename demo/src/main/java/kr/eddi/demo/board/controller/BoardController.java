package kr.eddi.demo.board.controller;

import kr.eddi.demo.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.board.entity.Board;
import kr.eddi.demo.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardController {

    @Autowired
    final private BoardService boardService;

    @PostMapping("/register")
    public Board boardRegister(@RequestBody BoardRegisterRequestForm form){
      Board registerBoard= boardService.boardRegister(form);
      log.info(registerBoard.toString());
    return registerBoard;
    }
    @PostMapping("/list")
    public List<Board> boardList(){
        List<Board> returnBoardList= boardService.list();
        log.info(returnBoardList.toString());
        return returnBoardList;
    }
//    @PostMapping("/read/{id}")
//    public Board readBoard (@PathVariable("id") Long id){
//        log.info("보드 가져와");
//        return boardService.read(id);
//    }
}
