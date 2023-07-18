package com.example.demo.board.dto;

import com.example.demo.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long boardId;
    private String writer;
    private String title;
    private String content;
    private Integer likeCount;
    private Integer readCount;
    private Integer replyCount;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.writer = board.getWriter();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likeCount = board.getLikeCount();
        this.readCount = board.getReadCount();
        this.replyCount = board.getReplyCount();
        this.createDate = board.getCreateDate();
        this.modifyDate = board.getModifyDate();
    }
}
