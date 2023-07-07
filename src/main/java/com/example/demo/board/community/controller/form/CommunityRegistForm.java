package com.example.demo.board.community.controller.form;

import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommunityRegistForm {
    String communityTitle;
    String communityContent;

    public CommunityBoard toCommunityBoard() {
        return new CommunityBoard(communityTitle, communityContent);
    }


}
