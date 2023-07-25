package com.example.demo.board.service;

import com.example.demo.board.controller.form.BoardCategoryListForm;
import com.example.demo.board.controller.form.BoardCategoryResponseForm;
import com.example.demo.board.controller.form.BoardResponseForm;
import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    final private BoardRepository boardRepository;

    @Override
    public List<Board> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }


    @Override
    public Board register(Board registerBoard) {
        return boardRepository.save(registerBoard);
    }

    @Override
    public Board read(Long boardId) {
        Optional<Board> maybeBoard = boardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            log.info("정보가 없습니다.");
            return null;
        }
        return maybeBoard.get();
    }

    @Override
    public void updateReadCount(Long boardId, Integer readCount) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
        board.updateReadCount(readCount);
        boardRepository.save(board);
        log.info("조회 카운트: {}", readCount);
    }


    @Override
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public Board modify(Long boardId, RequestBoardForm requestBoardForm) {
        Optional<Board> maybeBoard = boardRepository.findById(boardId);

        if (maybeBoard.isEmpty()) {
            log.info("정보가 없습니다.");
            return null;
        }
        Board board = maybeBoard.get();
        board.setTitle(requestBoardForm.getTitle());
        board.setContent(requestBoardForm.getContent());
        return boardRepository.save(board);
    }

    @Override
    public List<Board> search(String keyword) {
        List<Board> searchBoard = boardRepository.findByTitleContaining(keyword);
        return searchBoard;
    }
    @Override
    public List<BoardCategoryResponseForm> getListByCategory(BoardCategory category){
        List<Board> boardList = boardRepository.findAllCategory(category);

        List<BoardCategoryResponseForm> boardCategoryList = new ArrayList<>();
        for (Board board: boardList) {
            boardCategoryList.add(
                    new BoardCategoryResponseForm(
                            board.getBoardId(),board.getTitle(),board.getWriter(),board.getContent()));
        }
        return boardCategoryList;
    }

    @Override
    public BoardResponseForm getBoardsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        log.info("getBoardsByPage called with pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        Page<Board> boardPage = boardRepository.findAll(pageable);

        // BoardResponseForm 객체 생성과 데이터 설정
        BoardResponseForm boardResponse = new BoardResponseForm();
        boardResponse.setData(boardPage.getContent());
        boardResponse.setTotalPages(boardPage.getTotalPages());
        boardResponse.setCurrentPage(boardPage.getNumber());

        return boardResponse;
    }

    @Override
    public List<BoardCategoryListForm> getCategoryList() {
        List<BoardCategoryListForm> categoryList = new ArrayList<>();
        for (BoardCategory category: BoardCategory.values()) {
            Long posts = boardRepository.findPostNumberByCategory(category);
            log.info("posts:" +posts);
            categoryList.add(
                    new BoardCategoryListForm(category,posts));
        }
        return categoryList;
    }

}
