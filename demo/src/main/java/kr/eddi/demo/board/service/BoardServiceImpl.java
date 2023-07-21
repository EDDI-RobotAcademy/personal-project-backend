package kr.eddi.demo.board.service;

import kr.eddi.demo.board.controller.form.BoardModifyRequest;
import kr.eddi.demo.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.board.entity.Board;
import kr.eddi.demo.board.repository.BoardRepository;
import kr.eddi.demo.board.service.reqeust.BoardRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    final private  BoardRepository boardRepository;
    @Override
    public Board boardRegister(BoardRegisterRequestForm form) {
        Board board = boardRepository.save(form.toBoardRequest().toBoard());
        return board;
    }

    @Override
    public List<Board> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Board read(Long id){
        Optional<Board> maybeBoard= boardRepository.findById(id);
        if(maybeBoard.isPresent()){

            return maybeBoard.get();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Board modify(Long id, BoardModifyRequest request) {
        Optional<Board> maybeBoard = boardRepository.findById(id);
        if(maybeBoard.isEmpty()){
            return null;
        }
        Board board = maybeBoard.get();
        board.setBoardTitle(request.getBoardTitle());
        board.setBoardInfo(request.getBoardInfo());
        return boardRepository.save(board);
    }
}
