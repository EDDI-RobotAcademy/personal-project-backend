package com.example.demo.board.community.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommunityListForm {
    final private Long communityNumber;
    final private String communityTitle;
    final private LocalDateTime communityDate;

}
