package kr.eddi.demo.board.service;

import kr.eddi.demo.board.controller.form.BoardModifyRequest;
import kr.eddi.demo.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.board.entity.Board;
import kr.eddi.demo.board.service.reqeust.BoardRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardService {
    Board boardRegister(BoardRegisterRequestForm form);

    List<Board> list();

    Board read(Long id);

    void delete(Long id);

    Board modify(Long id, BoardModifyRequest request);

    List<Board> myBoards(String userToken);
    void increaseView(Long id);
    void countsComment();
}
