package com.example.demo.domain.song.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SongRegisterRequestForm {
    final private Long id;
    final private String title;
    final private String singer;
    final private String genre;
    final private String link;
}
