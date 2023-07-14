package com.example.demo.domain.song.service;

import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.domain.account.repository.AccountRepository;
import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.playlist.repository.PlaylistRepository;
import com.example.demo.domain.song.controller.form.SongRegisterRequestForm;
import com.example.demo.domain.song.entity.Song;
import com.example.demo.domain.song.repository.SongRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService{

    final private PlaylistRepository playlistRepository;
    final private SongRepository songRepository;
    final private AccountRepository accountRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Song register(SongRegisterRequestForm requestForm, HttpServletRequest request) {

        final Playlist playlist = playlistRepository.findWithSongById(requestForm.getPlaylistId());

        final Song song = new Song(requestForm.getTitle(), requestForm.getSinger(), requestForm.getGenre(), requestForm.getLink(), playlist);

        return songRepository.save(song);
    }

    @Override
    public int countSong(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }

        int count = songRepository.countSongByPlaylistId(playlistRepository.countPlaylistByAccountId(accountRepository.findByEmail(email).get().getId()));
        log.info("song count = " + count );
        return count;
    }

    @Override
    public Song read(Long id) {
        Song song = songRepository.findById(id).get();
        return song;
    }
}
