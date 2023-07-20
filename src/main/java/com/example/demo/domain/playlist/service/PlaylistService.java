package com.example.demo.domain.playlist.service;

import com.example.demo.domain.playlist.controller.form.PlaylistModifyRequestForm;
import com.example.demo.domain.playlist.controller.form.PlaylistReadResponseForm;
import com.example.demo.domain.playlist.controller.form.PlaylistRegisterRequestForm;
import com.example.demo.domain.playlist.entity.Playlist;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PlaylistService {
    Playlist register(PlaylistRegisterRequestForm requestForm, HttpServletRequest request);

    int countPlaylist(HttpServletRequest request);

    List<PlaylistReadResponseForm> list();

    PlaylistReadResponseForm read(Long id);

    Playlist modify(PlaylistModifyRequestForm requestForm);

    List<PlaylistReadResponseForm> listByLoginAccount(HttpServletRequest request);

    boolean delete(Long playlistId);

    int likePlaylist(Long playlistId, HttpServletRequest request);

    int unlikePlaylist(Long playlistId, HttpServletRequest request);

    Boolean isPlaylistLiked(Long playlistId, HttpServletRequest request);
}
