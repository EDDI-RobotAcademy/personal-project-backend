package com.example.demo.board.form;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResForm {

    private Long commentId;
    private String text;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private String createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private String modifiedDate;
    private MemberBoard memberBoard;
    private Member member;
    @Builder
    public CommentResForm(Long commentId, String text, String createdDate, String modifiedDate, MemberBoard memberBoard, Member member) {
        this.commentId = commentId;
        this.text = text;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.memberBoard = memberBoard;
        this.member = member;
    }
}
