package com.example.demo.board.service;

import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.repository.BoardCategoryRepository;
import com.example.demo.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    final private BoardRepository boardRepository;
    final private BoardCategoryRepository boardCategoryRepository;
    @Override
    public List<Board> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }


    @Override
    public Board register(Board registerBoard) {
        BoardCategory boardCategory = registerBoard.getCategory();
        boardCategory.setBoard(registerBoard); // BoardCategory와의 양방향 관계 설정
        BoardCategory savedCategory = boardCategoryRepository.save(boardCategory); // BoardCategory 저장

        registerBoard.setCategory(savedCategory); // 영속화된 BoardCategory 설정

        return boardRepository.save(registerBoard); // Board 저장
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
    public Board findById(Long boardId) {
        Optional<Board> maybeBoard = boardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            log.info("정보가 없습니다.");
            return null;
        }
        return maybeBoard.get();
    }

    @Override
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Override
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
}
