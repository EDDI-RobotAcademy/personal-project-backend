package com.example.demo.domain.song.repository;

import com.example.demo.domain.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
