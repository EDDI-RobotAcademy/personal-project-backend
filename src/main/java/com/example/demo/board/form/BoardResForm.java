package com.example.demo.board.form;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.comment.entity.Comment;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResForm {

    private Long boardId;
    private String title;
    private String nickname;
    private String content;
    private LocalDate createDate;
    private LocalDate updateDate;
    @Builder
    public BoardResForm(Long boardId, String title, String nickname, String content, LocalDate createDate, LocalDate updateDate, List<FilePaths> filePathList, Member member, List<CommentResForm> commentList) {
        this.boardId = boardId;
        this.title = title;
        this.nickname = nickname;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.filePathList = filePathList;
        this.member = member;
        this.commentList = commentList;
    }

    private List<FilePaths> filePathList;

    private Member member;

    private List<CommentResForm> commentList;

}
