package com.example.demo.board.community.controller.form;

import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.notice.entity.NoticeBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityRegistForm {
    final private String communityTitle;
    final private String communityContent;


    public CommunityBoard toCommunityBoard() {
        return new CommunityBoard(communityTitle, communityContent);
    }


}
