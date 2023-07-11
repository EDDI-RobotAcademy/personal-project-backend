package com.example.demo.domain.playlist.service;

import com.example.demo.domain.playlist.controller.form.PlaylistRegisterRequestForm;
import com.example.demo.domain.playlist.entity.Playlist;
import jakarta.servlet.http.HttpServletRequest;

public interface PlaylistService {
    Playlist register(PlaylistRegisterRequestForm requestForm, HttpServletRequest request);
}
