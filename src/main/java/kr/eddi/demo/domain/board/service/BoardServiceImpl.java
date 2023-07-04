package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.BoardModifyRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.entity.BoardList;
import kr.eddi.demo.domain.board.entity.StockBoardList;
import kr.eddi.demo.domain.board.repository.BoardListRepository;
import kr.eddi.demo.domain.board.repository.BoardRepository;
import kr.eddi.demo.domain.board.repository.StockBoardListRepository;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    final private BoardRepository boardRepository;
    final private StockRepository stockRepository;
    final private BoardListRepository boardListRepository;
    final private StockBoardListRepository stockBoardListRepository;

    @Override
    public List<Board> list(String ticker) {

        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(ticker);

        if (maybeStockBoardList.isEmpty()) {
            log.info("Have Any Boards");
            return null;
        }
        BoardList boardList = maybeStockBoardList.get().getBoardList();

        List<Board> getBoardList = boardList.getBoards();

        return getBoardList;
    }

    @Override
    public Board register(BoardRegisterRequestForm requestForm, String ticker) {

        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(ticker);

        if (maybeStockBoardList.isPresent()) {
        maybeStockBoardList.get().getBoardList().getBoards().add(requestForm.toBoardRegisterRequest().toBoard());
        return requestForm.toBoardRegisterRequest().toBoard();
        } else {

        log.info("no exist boards");
        Board board = requestForm.toBoardRegisterRequest().toBoard();
        Stock stock = stockRepository.findByStockTicker(ticker).get();

        boardRepository.save(board);

        BoardList boardList = new BoardList();
        board.setBoardList(boardList);
        boardListRepository.save(boardList);

        StockBoardList stockBoardList = new StockBoardList(stock, boardList);
        stockBoardListRepository.save(stockBoardList);

        return board;
        }
    }

    @Override
    public Board read(String ticker, Long id) {
        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(ticker);

        if (maybeStockBoardList.isPresent()) {
            List<Board> boardList = maybeStockBoardList.get().getBoardList().getBoards();
            for (Board board : boardList) {
                if (board.getId().equals(id)) {
                    return board;
                }
            }
        }return null;
    }

    @Override
    public void modify(BoardRegisterRequestForm requestForm, String ticker, Long id) {

        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(ticker);

        if (maybeStockBoardList.isPresent()) {
            List<Board> boardList = maybeStockBoardList.get().getBoardList().getBoards();
            for (Board board : boardList) {
                if (board.getId().equals(id)) {

                    board.setTitle(requestForm.getTitle());
                    board.setContent(requestForm.getContent());
                }
            }
        }
    }

    @Override
    public void delete(String ticker, Long id) {
        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(ticker);

        if (maybeStockBoardList.isPresent()) {
            List<Board> boardList = maybeStockBoardList.get().getBoardList().getBoards();
            for (Board board : boardList) {
                if (board.getId().equals(id)) {
                    boardRepository.delete(board);
                }
            }
        }
    }
}
