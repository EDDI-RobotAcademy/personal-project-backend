package com.example.demo.domain.playlist.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaylistModifyRequestForm {
    final private Long id;
    final private String title;
}
