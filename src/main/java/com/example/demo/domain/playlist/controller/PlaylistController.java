package com.example.demo.domain.playlist.controller;

import com.example.demo.domain.playlist.controller.form.PlaylistModifyRequestForm;
import com.example.demo.domain.playlist.controller.form.PlaylistReadResponseForm;
import com.example.demo.domain.playlist.controller.form.PlaylistRegisterRequestForm;
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
    public long playlistRegister (@RequestBody PlaylistRegisterRequestForm requestForm, HttpServletRequest request) {
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
        return playlistService.read(id);
    }

    @PostMapping("/modify")
    public boolean modifyPlaylist(@RequestBody PlaylistModifyRequestForm requestForm, HttpServletRequest request){
        return playlistService.modify(requestForm);
    }

    @PostMapping("list-by-login-account/{page}")
    public List<PlaylistReadResponseForm> playlistByLoginAccount(@PathVariable("page") int page, HttpServletRequest request){
        return playlistService.listByLoginAccount(page, request);
    }

    @DeleteMapping("/{playlistId}")
    public boolean deleteSong(@PathVariable("playlistId") Long playlistId, HttpServletRequest request){
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

    @PostMapping("/slice-list/{page}")
    public List<PlaylistReadResponseForm> slicePlaylist(@PathVariable("page") int page, HttpServletRequest request){
        return playlistService.slicePlaylist(page);
    }

    @PostMapping("/sort-slice-list/{page}")
    public List<PlaylistReadResponseForm> sortSlicePlaylist(@PathVariable("page") int page, HttpServletRequest request){
        return playlistService.sortByLikersSlicePlaylist(page);
    }

    @PostMapping("/count-all-playlist")
    public long countAllPlaylist(){
        return playlistService.countAllPlaylist();
    }

    @PostMapping("/count-playlist-by-account")
    public long countByLoginAccount(HttpServletRequest request){
        return playlistService.countTotalPageByLoginAccount(request); // 로그인 계정 플레이리스트 카운트
    }
}
