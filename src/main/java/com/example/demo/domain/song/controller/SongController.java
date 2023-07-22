package com.example.demo.domain.song.controller;

import com.example.demo.domain.song.controller.form.SongModifyRequestForm;
import com.example.demo.domain.song.controller.form.SongRegisterRequestForm;
import com.example.demo.domain.song.entity.Song;
import com.example.demo.domain.song.service.SongService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@RequestMapping("/song")
@RestController
@RequiredArgsConstructor
public class SongController {
    final private SongService songService;

    @PostMapping("/register")
    public Long songRegister (@RequestBody SongRegisterRequestForm requestForm, HttpServletRequest request) throws GeneralSecurityException, IOException {

        return songService.register(requestForm, request);
    }

    @PostMapping("count-song")
    public int countSong(HttpServletRequest request){
        return songService.countSong(request);
    }

    @GetMapping("/{id}")
    public Song readSong(@PathVariable("id") Long id, HttpServletRequest request){
        Song song = songService.read(id);
        return song;
    }

    @PostMapping("/modify")
    public Song modifySong(@RequestBody SongModifyRequestForm requestForm, HttpServletRequest request){

        songService.modify(requestForm);
        return null;
    }

    @DeleteMapping("/{songId}")
    public boolean deleteSong(@PathVariable("songId") Long songId, HttpServletRequest request){
        log.info("songId : " + songId);
        return songService.delete(songId);
    }
}
