package com.example.demo.board.form;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CommentResForm {

    private Long commentId;
    private String text;
    private String nickname;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private MemberBoard memberBoard;
    private Member member;
    @Builder
    public CommentResForm(Long commentId, String text, LocalDate createdDate, LocalDate modifiedDate, MemberBoard memberBoard, Member member) {
        this.commentId = commentId;
        this.text = text;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.memberBoard = memberBoard;
        this.member = member;
    }
}
