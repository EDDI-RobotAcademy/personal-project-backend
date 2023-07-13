package com.example.demo.domain.song.repository;

import com.example.demo.domain.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    int countSongByPlaylistId(int countPlaylistByAccountId);

    @Query("SELECT s FROM Song s JOIN FETCH s.playlist p JOIN FETCH p.account WHERE s.id = :id")
    Optional<Song> findById(Long id);
}
