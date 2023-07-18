package com.example.demo.domain.song.entity;

import com.example.demo.domain.playlist.entity.Playlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String singer;

    @Setter
    private String genre;

    @Setter
    private String link;

    @Lob
    @Setter
    @Column(name="LYRICS", length=4000)
    private String lyrics;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="playlist_id")
    private Playlist playlist;

    public Song(String title, String singer, String genre, String link, Playlist playlist) {
        this.title = title;
        this.singer = singer;
        this.genre = genre;
        this.link = link;
        this.playlist = playlist;
    }
}
