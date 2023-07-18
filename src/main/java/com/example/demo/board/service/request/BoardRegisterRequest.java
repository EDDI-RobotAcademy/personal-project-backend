package com.example.demo.board.service.request;

import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.repository.BoardCategoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BoardRegisterRequest {
    final private String writer;
    final private String title;
    final private String content;
    final private Long category;

    public Board toBoard() {
        BoardCategory boardCategory = BoardCategory.fromValue(category.intValue());
        return new Board(writer, title, content, 0, 0, 0, boardCategory);
    }
}


