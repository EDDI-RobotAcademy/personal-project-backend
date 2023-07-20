package com.example.demo.domain.playlist.service;

import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.account.repository.AccountRepository;
import com.example.demo.domain.playlist.controller.form.PlaylistModifyRequestForm;
import com.example.demo.domain.playlist.controller.form.PlaylistReadResponseForm;
import com.example.demo.domain.playlist.controller.form.PlaylistRegisterRequestForm;
import com.example.demo.domain.playlist.entity.Playlist;
import com.example.demo.domain.playlist.repository.PlaylistRepository;
import com.example.demo.domain.song.entity.Song;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService{

    final private PlaylistRepository playlistRepository;

    final private AccountRepository accountRepository;

    final private JwtTokenUtil jwtTokenUtil;
    @Override
    public Playlist register(PlaylistRegisterRequestForm requestForm, HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findWithPlaylistByEmail(email);

        final Playlist playlist = new Playlist(requestForm.getTitle(), account);

        return playlistRepository.save(playlist);
    }

    @Override
    public int countPlaylist(HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        int count = playlistRepository.countPlaylistByAccountId(accountRepository.findByEmail(email).get().getId());
        log.info("playlist count = " + count );
        return count;
    }

    @Override
    @Transactional
    public List<PlaylistReadResponseForm> list() {
        List<Playlist> playlists = playlistRepository.findAll();

        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();
        for(Playlist playlist : playlists){
            PlaylistReadResponseForm responseForm = new PlaylistReadResponseForm(playlist, playlist.getSongList(), playlist.getLikers().size());
            responseForms.add(responseForm);
        }
        return responseForms;
    }

    @Override
    public PlaylistReadResponseForm read(Long id) {
        Playlist playlist = playlistRepository.findWithSongById(id);

        List<Song> resultList = playlist.getSongList();
        List<Song> distinctResult = resultList.stream().distinct().collect(Collectors.toList());

        return new PlaylistReadResponseForm(playlist, distinctResult, playlist.getLikers().size());
    }

    @Override
    public Playlist modify(PlaylistModifyRequestForm requestForm) {
        Optional<Playlist> maybePlaylist = playlistRepository.findById(requestForm.getId());

        if(maybePlaylist.isEmpty()){
            return null;
        }
        Playlist playlist = maybePlaylist.get();

        playlist.setTitle(requestForm.getTitle());

        return playlistRepository.save(playlist);
    }

    @Override
    public List<PlaylistReadResponseForm> listByLoginAccount(HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        List<Playlist> playlists = playlistRepository.findPlaylistByAccountId(accountRepository.findByEmail(email).get());

        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();
        for (Playlist playlist : playlists) {
            PlaylistReadResponseForm responseForm = new PlaylistReadResponseForm(playlist, playlist.getSongList(), playlist.getLikers().size());
            responseForms.add(responseForm);
        }
        return responseForms;
    }

    @Override
    public boolean delete(Long playlistId) {
        Optional<Playlist> maybeSong = playlistRepository.findById(playlistId);
        if(maybeSong.isEmpty()){
            return false;
        }

        playlistRepository.deleteById(playlistId);
        return true;
    }

    @Override
    public int likePlaylist(Long playlistId, HttpServletRequest request) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (account.getLikedPlaylists().contains(playlist)) {
            return playlist.getLikers().size();
        }

        account.getLikedPlaylists().add(playlist);
        accountRepository.save(account);
        playlist.getLikers().add(account);
        playlistRepository.save(playlist);

        return playlist.getLikers().size();
    }

    @Override
    public int unlikePlaylist(Long playlistId, HttpServletRequest request) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getLikedPlaylists().contains(playlist)) {
            return playlist.getLikers().size();
        }

        account.getLikedPlaylists().remove(playlist);
        accountRepository.save(account);
        playlist.getLikers().remove(account);
        playlistRepository.save(playlist);

        return playlist.getLikers().size();
    }

    @Override
    public Boolean isPlaylistLiked(Long playlistId, HttpServletRequest request) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Set<Playlist> likedPlaylists = account.getLikedPlaylists();

        return likedPlaylists.contains(playlist);
    }
}