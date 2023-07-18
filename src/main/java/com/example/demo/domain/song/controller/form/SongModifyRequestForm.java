package com.example.demo.domain.song.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SongModifyRequestForm {
    final private Long songId;
    final private String title;
    final private String singer;
    final private String genre;
    final private String link;
}
