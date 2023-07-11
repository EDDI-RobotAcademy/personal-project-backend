package com.example.demo.domain.playlist.controller;

import com.example.demo.domain.playlist.controller.form.PlaylistRegisterRequestForm;
import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.playlist.service.PlaylistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/playlist")
@RestController
public class PlaylistController {

    final private PlaylistService playlistService;

    @PostMapping("/register")
    public Playlist playlistRegister (@RequestBody PlaylistRegisterRequestForm requestForm, HttpServletRequest request) {
        log.info(requestForm.getTitle());
        return playlistService.register(requestForm, request);
    }
}
