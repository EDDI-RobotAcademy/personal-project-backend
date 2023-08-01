package com.example.demo.board.community.controller.form;

import com.example.demo.board.community.entity.CommunityBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityModifyForm {

    final private Long communityId;
    final private String title;
    final private String content;

    public CommunityBoard toCommunityBoard() {
        return new CommunityBoard(communityId,title,content);
    }
}
