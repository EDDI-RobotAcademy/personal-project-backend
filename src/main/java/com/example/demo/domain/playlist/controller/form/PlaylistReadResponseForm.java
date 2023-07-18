package com.example.demo.domain.playlist.controller.form;

import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.song.entity.Song;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class PlaylistReadResponseForm {
    final private Playlist playlist;
    final private List<Song> songList;
    final private int likeCount;
}
