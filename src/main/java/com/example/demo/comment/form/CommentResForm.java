package com.example.demo.comment.form;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResForm {

    private Long commentId;
    private String text;
    private Member member;
    private MemberBoard memberBoard;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    @Builder
    public CommentResForm(Long commentId, String text, Member member, MemberBoard memberBoard, LocalDate createdDate, LocalDate modifiedDate) {
        this.commentId = commentId;
        this.text = text;
        this.member = member;
        this.memberBoard = memberBoard;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
