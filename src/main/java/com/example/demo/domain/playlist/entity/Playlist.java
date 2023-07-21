package com.example.demo.domain.playlist.entity;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.song.entity.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Song> songList = new ArrayList<>();

    @ManyToMany(mappedBy = "likedPlaylists", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Account> likers = new HashSet<>();

    public Playlist(String title, Account account) {
        this.title = title;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void removeFromLikers(Account liker) {
        likers.remove(liker);
    }
}