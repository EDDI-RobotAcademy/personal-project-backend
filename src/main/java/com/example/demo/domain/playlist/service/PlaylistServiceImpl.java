package com.example.demo.domain.playlist.service;

import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.account.repository.AccountRepository;
import com.example.demo.domain.playlist.controller.form.PlaylistModifyRequestForm;
import com.example.demo.domain.playlist.controller.form.PlaylistReadResponseForm;
import com.example.demo.domain.playlist.controller.form.PlaylistRegisterRequestForm;
import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.playlist.repository.PlaylistRepository;
import com.example.demo.domain.song.entity.Song;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService{

    @Value("${jwt.secret}")
    private String secretKey;

    final private PlaylistRepository playlistRepository;

    final private AccountRepository accountRepository;
    @Override
    public Playlist register(PlaylistRegisterRequestForm requestForm, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }

        Account account = accountRepository.findWithPlaylistByEmail(email);

        final Playlist playlist = new Playlist(requestForm.getTitle(), account);

        return playlistRepository.save(playlist);
    }

    @Override
    public int countPlaylist(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }

        int count = playlistRepository.countPlaylistByAccountId(accountRepository.findByEmail(email).get().getId());
        log.info("playlist count = " + count );
        return count;
    }

    @Override
    @Transactional
    public List<PlaylistReadResponseForm> list() {
        List<Playlist> playlists = playlistRepository.findAll();
        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();
        for(Playlist playlist : playlists){
            PlaylistReadResponseForm responseForm = new PlaylistReadResponseForm(playlist, playlist.getSongList());
            responseForms.add(responseForm);
        }
        return responseForms;
    }

    @Override
    public PlaylistReadResponseForm read(Long id) {
        Playlist playlist = playlistRepository.findWithSongById(id);

        return new PlaylistReadResponseForm(playlist, playlist.getSongList());
    }

    @Override
    public boolean modify(PlaylistModifyRequestForm requestForm) {
        Optional<Playlist> maybePlaylist = playlistRepository.findById(requestForm.getId());

        if(maybePlaylist.isEmpty()){
            return false;
        }
        Playlist playlist = maybePlaylist.get();

        playlist.setTitle(requestForm.getTitle());

        playlistRepository.save(playlist);

        return true;
    }

    @Override
    public List<Playlist> listByLoginAccount(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }

        return playlistRepository.findPlaylistByAccountId(accountRepository.findByEmail(email).get());
    }

    @Override
    public boolean delete(Long playlistId) {
        Optional<Playlist> maybeSong = playlistRepository.findById(playlistId);
        if(maybeSong.isEmpty()){
            return false;
        }

        playlistRepository.deleteById(playlistId);
        return true;
    }
}