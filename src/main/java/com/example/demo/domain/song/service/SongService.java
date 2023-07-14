package com.example.demo.domain.song.service;

import com.example.demo.domain.song.controller.form.SongModifyRequestForm;
import com.example.demo.domain.song.controller.form.SongRegisterRequestForm;
import com.example.demo.domain.song.entity.Song;
import jakarta.servlet.http.HttpServletRequest;

public interface SongService {
    Long register(SongRegisterRequestForm requestForm, HttpServletRequest request);

    int countSong(HttpServletRequest request);

    Song read(Long id);
    boolean modify(SongModifyRequestForm requestForm);
}
