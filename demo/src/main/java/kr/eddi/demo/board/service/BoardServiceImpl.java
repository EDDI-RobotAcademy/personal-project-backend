package kr.eddi.demo.board.service;

import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.repository.AccountRepository;

import kr.eddi.demo.board.controller.form.BoardModifyRequest;
import kr.eddi.demo.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.board.entity.Board;
import kr.eddi.demo.board.repository.BoardRepository;

import kr.eddi.demo.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    final private AccountRepository accountRepository;
    final private  BoardRepository boardRepository;
    @Autowired
    final private RedisService redisService;
    @Override
    public Board boardRegister(BoardRegisterRequestForm form) {
        return boardRepository.save(form.toBoardRequest().toBoard());
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
        board.setBoardTransport(request.getBoardTransport());
        log.info(request.getBoardTransport()+"왜 아난옴");
        return boardRepository.save(board);
    }

    @Override
    public List<Board> myBoards(String userToken) {
        Long id = redisService.getValueByKey(userToken);
        log.info("찾은 id: "+id);
        Optional<Account> maybeAccount= accountRepository.findById(id);
        if (maybeAccount.isEmpty()){
            return null;
        }
        String nickName=maybeAccount.get().getNickname();

        return boardRepository.findByWriter(nickName);
    }
}
