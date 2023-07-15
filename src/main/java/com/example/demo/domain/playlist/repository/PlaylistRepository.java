package com.example.demo.domain.playlist.repository;

import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.playlist.entity.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("SELECT DISTINCT p FROM Playlist p LEFT JOIN FETCH p.songList LEFT JOIN FETCH p.account WHERE p.id = :id")
    Playlist findWithSongById(Long id);

    @Query("SELECT p FROM Playlist p JOIN FETCH p.account")
    List<Playlist> findAll();

    int countPlaylistByAccountId(Long accountId);

    @Query("SELECT p.id FROM Playlist p where p.account = :account")
    List<Long> findPlaylistIdByAccountId(Account account);

    @Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.account where p.account = :account")
    List<Playlist> findPlaylistByAccountId(Account account);

    @Modifying
    @Transactional
    void deleteByAccountId(Long accountId);
}
