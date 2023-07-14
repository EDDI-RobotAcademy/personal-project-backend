package com.example.demo.domain.playlist.controller;

import com.example.demo.domain.playlist.controller.form.PlaylistModifyRequestForm;
import com.example.demo.domain.playlist.controller.form.PlaylistReadResponseForm;
import com.example.demo.domain.playlist.controller.form.PlaylistRegisterRequestForm;
import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.playlist.service.PlaylistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/count-playlist")
    public int countPlaylist(HttpServletRequest request){
        return playlistService.countPlaylist(request);
    }

    @PostMapping("/list")
    public List<Playlist> playList(HttpServletRequest request){
        List<Playlist> play = playlistService.list();
        for(Playlist p : play){
            log.info(p.getTitle());
        }
        return play;
    }

    @GetMapping("/{id}")
    public PlaylistReadResponseForm readPlayList(@PathVariable("id") Long id, HttpServletRequest request){
        PlaylistReadResponseForm responseForm = playlistService.read(id);
        return responseForm;
    }

    @PostMapping("/modify")
    public Playlist modifyPlaylist(@RequestBody PlaylistModifyRequestForm requestForm, HttpServletRequest request){
        log.info(String.valueOf(requestForm.getId()));
        log.info(requestForm.getTitle());
        playlistService.modify(requestForm);
        return null;
    }
}
