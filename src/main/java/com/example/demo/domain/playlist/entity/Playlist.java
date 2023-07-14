package com.example.demo.domain.playlist.entity;

import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.song.entity.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Song> songList;

    public Playlist(String title, Account account) {
        this.title = title;
        this.account = account;
    }
}
