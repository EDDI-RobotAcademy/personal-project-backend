package com.example.demo.boardTest;

import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import com.example.demo.board.controller.form.BoardRegisterForm;
import com.example.demo.board.controller.form.CategoryBoardListResponseForm;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.service.BoardService;
import com.example.demo.board.service.request.BoardRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demo.board.entity.BoardCategory.Asia;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("게시물 등록 테스트")
    void registerBoardTest () {
        final Long accountId = 1l;
        final String title = "test board title";
        final String content = "test board content";
        final BoardCategory category = Asia;

        Account account = accountService.findAccountById(accountId);
        String writer = account.getNickname();


        BoardRegisterRequest registerRequest = new BoardRegisterRequest(title, writer, content, category);

        Board board = boardService.register(registerRequest);
        assertEquals(title, board.getTitle());
        assertEquals(content, board.getContent());
        assertEquals(writer, board.getWriter());
        assertEquals(category, board.getBoardCategory());
    }

    @Test
    @DisplayName("게시글 불러오기 테스트")
    void readBoardTest () {
        final Long boardId = 1l;
        final String title = "test board title";
        final String content = "test board content";

        Board board = boardService.read(boardId);

        assertEquals(title, board.getTitle());
        assertEquals(content, board.getContent());
    }

    @Test
    @DisplayName("카테고리별 게시글 리스트 불러오기")
    void bringCategorizedBoardList () {
        final BoardCategory category = Asia;
        final String title = "test board title";
        final Long boardId = 1l;

        List<CategoryBoardListResponseForm> responseFormList = boardService.getListByCategory(category);
        CategoryBoardListResponseForm responseForm = responseFormList.get(0);

        assertEquals(title, responseForm.getTitle());
        assertEquals(boardId, responseForm.getBoardId());
    }
}
