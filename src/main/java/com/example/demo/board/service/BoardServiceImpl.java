package com.example.demo.board.service;

import com.example.demo.board.controller.form.BoardCategoryListForm;
import com.example.demo.board.controller.form.BoardCategoryResponseForm;
import com.example.demo.board.controller.form.BoardResponseForm;
import com.example.demo.board.controller.form.RequestBoardForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.entity.BoardLike;
import com.example.demo.board.repository.BoardLikeRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    final private BoardRepository boardRepository;
    final private BoardLikeRepository boardLikeRepository;
    public void mkdir(String path) {
        File folder = new File(path);
        if (!folder.exists()) {	// 폴더가 존재하는지 체크, 없다면 생성
            if (folder.mkdir())
                System.out.println("폴더 생성 성공");
            else
                System.out.println("폴더 생성 실패");
        } else {	// 폴더가 존재한다면
            System.out.println("폴더가 이미 존재합니다.");
        }
    }

    public String makeTxtPath(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar time = Calendar.getInstance();
        String path =  File.separator + name;
        this.mkdir(System.getProperty("user.dir") + path);
        path += File.separator + UUID.randomUUID().toString();
        this.mkdir(System.getProperty("user.dir") + path);
        String fileName = name + "_" + sdf.format(time.getTime()) + ".txt";
        String fullPath = path + File.separator + fileName;
        return fullPath;
    }

    public String makeBoardTxt(String filePath, String content) {
        try {
            File file = new File(System.getProperty("user.dir") + filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(content);
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public String getBoardTxt(String fileName) {
        try {
            FileInputStream fileStream = new FileInputStream(System.getProperty("user.dir") + fileName);
            byte[ ] readBuffer = new byte[fileStream.available()];
            while (fileStream.read( readBuffer ) != -1){}
            String text = new String(readBuffer);
            fileStream.close(); //스트림 닫기
            return text;
        } catch (IOException err) {
            return err.getMessage();
        }

    }

    @Override
    public List<Board> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }


    @Override
    public Board register(Board registerBoard) {
        String fileName = this.makeBoardTxt(makeTxtPath("Board"),registerBoard.getContent());
        registerBoard.setContent(fileName);
        return boardRepository.save(registerBoard);
    }

    @Override
    public Board read(Long boardId) {
        Optional<Board> maybeBoard = boardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            log.info("정보가 없습니다.");
            return null;
        }
        Board board = maybeBoard.get();
        board.setContent(getBoardTxt(board.getContent()));

        return board;
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
        String fileName = this.makeBoardTxt(board.getContent(), requestBoardForm.getContent());
        board.setContent(fileName);
        board.getBoardCategory();
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
        log.info("getBoardsByPage called with pageNumber: {}, pageSize: {}", Optional.of(pageNumber), Optional.of(pageSize));
        Page<Board> boardPage = boardRepository.findAll(pageable);

        // BoardResponseForm 객체 생성과 데이터 설정
        BoardResponseForm boardResponse = new BoardResponseForm();
        boardResponse.setData(boardPage.getContent());
        boardResponse.setTotalPages(boardPage.getTotalPages());
        boardResponse.setCurrentPage(boardPage.getNumber());

        return boardResponse;
    }

    @Override
    public void addLikeCount(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        List<BoardLike> boardLike = boardLikeRepository.findByBoard_BoardIdAndUserId(boardId,userId);
        if (boardLike.isEmpty()) {
            boardLikeRepository.save(new BoardLike(userId , board));
        } else {
            throw new RuntimeException("이미 해당 게시물을 좋아합니다");
        }
    }

    @Override
    public int getLikeCount(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            return board.getLikes().size();
        }
        return 0;
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
