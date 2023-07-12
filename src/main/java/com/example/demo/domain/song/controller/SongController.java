package com.example.demo.domain.song.controller;

import com.example.demo.domain.song.controller.form.SongRegisterRequestForm;
import com.example.demo.domain.song.entity.Song;
import com.example.demo.domain.song.service.SongService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/song")
@RestController
@RequiredArgsConstructor
public class SongController {
    final private SongService songService;

    @PostMapping("/register")
    public Song songRegister (@RequestBody SongRegisterRequestForm requestForm, HttpServletRequest request) {

        return songService.register(requestForm, request);
    }
}
