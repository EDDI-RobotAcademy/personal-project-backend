package com.example.demo.comment.form;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.comment.entity.Comment;
import com.example.demo.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@RequiredArgsConstructor
public class RequestRegisterCommentForm {

    final private String text;
    final private String nickname;
    final private String userToken;
    final private Long BoardId;

    public Comment toComment(MemberBoard memberBoard, Member member) {
        return new Comment(text, memberBoard, member);
    }

}
