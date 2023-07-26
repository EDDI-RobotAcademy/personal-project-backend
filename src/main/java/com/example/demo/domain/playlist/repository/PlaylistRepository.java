package com.example.demo.domain.playlist.repository;

import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.playlist.entity.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("SELECT DISTINCT p FROM Playlist p LEFT JOIN FETCH p.songList LEFT JOIN FETCH p.account LEFT JOIN FETCH p.likers WHERE p.id = :id")
    Playlist findWithSongById(Long id);

    @Query("SELECT p FROM Playlist p JOIN FETCH p.account JOIN FETCH p.songList LEFT JOIN FETCH p.likers")
    List<Playlist> findAll();

    int countPlaylistByAccountId(Long accountId);

    @Query("SELECT p FROM Playlist p JOIN FETCH p.songList LEFT JOIN FETCH p.likers ORDER BY p.id DESC")
    Slice<Playlist> slicePlaylist(Pageable pageable);

    @Query("SELECT p FROM Playlist p JOIN FETCH p.songList LEFT JOIN FETCH p.likers ORDER BY SIZE(p.likers) DESC")
    Slice<Playlist> sortByLikersSlicePlaylist(Pageable pageable);

    @Query("SELECT p.id FROM Playlist p where p.account = :account")
    List<Long> findPlaylistIdByAccountId(Account account);

    @Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.account LEFT JOIN FETCH p.songList LEFT JOIN FETCH p.likers where p.account = :account")
    Slice<Playlist> findPlaylistByAccountId(Account account,Pageable pageable);

    @Modifying
    @Transactional
    void deleteByAccountId(Long accountId);
}
