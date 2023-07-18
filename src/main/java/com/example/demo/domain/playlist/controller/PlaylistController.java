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
    public List<PlaylistReadResponseForm> playList(HttpServletRequest request){
        return playlistService.list();
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

    @PostMapping("list-by-login-account")
    public List<Playlist> playlistByLoginAccount(HttpServletRequest request){
        return playlistService.listByLoginAccount(request);
    }

    @DeleteMapping("/{playlistId}")
    public boolean deleteSong(@PathVariable("playlistId") Long playlistId, HttpServletRequest request){
        log.info("playlistId : " + playlistId);
        return playlistService.delete(playlistId);
    }

    @PostMapping("check-liked/{playlistId}")
    public boolean checkLikedPlaylist(@PathVariable("playlistId") Long playlistId, HttpServletRequest request){
        return playlistService.isPlaylistLiked(playlistId, request);
    }

    @PostMapping("/like-playlist/{playlistId}")
    public int likePlaylist (@PathVariable("playlistId") Long playlistId, HttpServletRequest request) {
        return playlistService.likePlaylist(playlistId, request);
    }

    @PostMapping("/unlike-playlist/{playlistId}")
    public int unLikePlaylist (@PathVariable("playlistId") Long playlistId, HttpServletRequest request) {
        return playlistService.unlikePlaylist(playlistId, request);
    }
}
