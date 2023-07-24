package com.example.demo.domain.song.service;

import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.domain.account.repository.AccountRepository;
import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.playlist.repository.PlaylistRepository;
import com.example.demo.domain.song.controller.form.SongModifyRequestForm;
import com.example.demo.domain.song.controller.form.SongRegisterRequestForm;
import com.example.demo.domain.song.entity.Song;
import com.example.demo.domain.song.repository.SongRepository;
import com.example.demo.utility.Youtube;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:youtube.properties")
public class SongServiceImpl implements SongService{

    final private PlaylistRepository playlistRepository;
    final private SongRepository songRepository;
    final private AccountRepository accountRepository;
    final private JwtTokenUtil jwtTokenUtil;
    final private Youtube youtube;

    @Value("${youtube.lyricsAddress}")
    private String lyricsAddress;


    @Override
    public Long register(SongRegisterRequestForm requestForm, HttpServletRequest request) throws GeneralSecurityException, IOException {

        final Playlist playlist = playlistRepository.findWithSongById(requestForm.getPlaylistId());
        final Song song = new Song(requestForm.getTitle(), requestForm.getSinger(), requestForm.getGenre(), requestForm.getLink(), playlist);
        if(requestForm.getLink().equals("")){
            String videoId = youtube.searchByKeyword(requestForm.getSinger() + " " + requestForm.getTitle());
            song.setLink("https://www.youtube.com/watch?v=" + videoId);
        }

        song.setLyrics(getLyrics(requestForm.getSinger() + " " + requestForm.getTitle()));

        songRepository.save(song);
        log.info(String.valueOf(song.getId()));
        return song.getId();
    }

    @Override
    public int countSong(HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);
        List<Long> counting = playlistRepository.findPlaylistIdByAccountId(accountRepository.findByEmail(email).get());

        log.info("playlistId = " + counting);
        int count = 0;
        for(long i : counting){
            count += songRepository.countSongByPlaylistId(i);
        }
        log.info("song count = " + count );
        return count;
    }

    @Override
    public Song read(Long id) {
        Song song = songRepository.findById(id).get();
        return song;
    }

    @Override
    public boolean modify(SongModifyRequestForm requestForm) {
        Optional<Song> maybeSong = songRepository.findById(requestForm.getSongId());

        if(maybeSong.isEmpty()){
            return false;
        }
        Song song= maybeSong.get();

        song.setTitle(requestForm.getTitle());
        song.setSinger(requestForm.getSinger());
        song.setGenre(requestForm.getGenre());
        song.setLink(requestForm.getLink());
        song.setLyrics(getLyrics(requestForm.getSinger() + " " + requestForm.getTitle()));

        songRepository.save(song);

        return true;
    }

    @Override
    public boolean delete(Long songId) {
        Optional<Song> maybeSong = songRepository.findById(songId);
        if(maybeSong.isEmpty()){
            return false;
        }

        songRepository.deleteById(songId);
        return true;
    }

    public String getLyrics(String searchWord) {
        String url = "http://" + lyricsAddress + "/get_lyrics?song_title=" + searchWord;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String lyrics = null;
        if (response.hasBody()) {
            lyrics = response.getBody();
            if (lyrics != null) {
                log.info(lyrics);
            } else {
                return null;
            }
        }
        lyrics = lyrics.replaceAll("\"", "");

        return lyrics;
    }
}
