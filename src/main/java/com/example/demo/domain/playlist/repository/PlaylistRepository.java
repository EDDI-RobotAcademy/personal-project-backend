package com.example.demo.domain.playlist.repository;

import com.example.demo.domain.playlist.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("SELECT DISTINCT p FROM Playlist p LEFT JOIN FETCH p.songList LEFT JOIN FETCH p.account WHERE p.id = :id")
    Playlist findWithSongById(Long id);

    @Query("SELECT p FROM Playlist p JOIN FETCH p.account")
    List<Playlist> findAll();

    int countPlaylistByAccountId(Long accountId);
}
