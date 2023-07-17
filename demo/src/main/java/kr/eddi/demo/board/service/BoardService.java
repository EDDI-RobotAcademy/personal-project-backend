package kr.eddi.demo.board.service;

import kr.eddi.demo.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.board.entity.Board;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardService {
    Board boardRegister(BoardRegisterRequestForm form);

    List<Board> list();

    Board read(Long id);

    void delete(Long id);
}
