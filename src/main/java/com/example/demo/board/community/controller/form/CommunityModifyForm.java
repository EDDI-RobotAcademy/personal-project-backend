package com.example.demo.board.community.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityModifyForm {

    final private Long communityNumber;
    final private String communityTitle;
    final private String communityContent;
}
