package com.example.demo.domain.song.service;

import com.example.demo.domain.song.controller.form.SongModifyRequestForm;
import com.example.demo.domain.song.controller.form.SongRegisterRequestForm;
import com.example.demo.domain.song.entity.Song;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface SongService {
    Long register(SongRegisterRequestForm requestForm, HttpServletRequest request) throws GeneralSecurityException, IOException;

    int countSong(HttpServletRequest request);

    Song read(Long id);
    boolean modify(SongModifyRequestForm requestForm);

    boolean delete(Long songId);
}
