package com.example.demo.board.community.controller.form;

import com.example.demo.account.entity.Account;
import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityRegistForm {
    final private String title;
    final private String content;
    final private Long accountId;


    public CommunityBoard toCommunityBoard() {
        return new CommunityBoard(title, content,accountId);
    }


}
