package com.example.demo.board.community.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommunityListForm {
    final private Long communityId;
    final private String title;
    final private LocalDateTime date;

}
