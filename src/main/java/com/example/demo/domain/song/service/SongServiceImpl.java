package com.example.demo.domain.song.service;

import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.playlist.repository.PlaylistRepository;
import com.example.demo.domain.song.controller.form.SongRegisterRequestForm;
import com.example.demo.domain.song.entity.Song;
import com.example.demo.domain.song.repository.SongRepository;
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

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Song register(SongRegisterRequestForm requestForm, HttpServletRequest request) {

        final Playlist playlist = playlistRepository.findWithSongById(requestForm.getId());

        final Song song = new Song(requestForm.getTitle(), requestForm.getSinger(), requestForm.getGenre(), requestForm.getLink(), playlist);

        return songRepository.save(song);
    }
}
