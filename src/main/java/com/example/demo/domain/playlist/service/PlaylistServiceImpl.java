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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    final private PlaylistRepository playlistRepository;

    final private AccountRepository accountRepository;

    final private JwtTokenUtil jwtTokenUtil;
    final int PAGE_SIZE = 6;

    @Override
    public long register(PlaylistRegisterRequestForm requestForm, HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findWithPlaylistByEmail(email);

        final Playlist playlist = new Playlist(requestForm.getTitle(), account);

        return playlistRepository.save(playlist).getId();
    }

    @Override
    public int countPlaylist(HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

        return playlistRepository.countPlaylistByAccountId(account.getId());
    }

    @Override
    @Transactional
    public List<PlaylistReadResponseForm> slicePlaylist(int page) {
        Slice<Playlist> playlists = playlistRepository.slicePlaylist(PageRequest.of(page - 1, PAGE_SIZE));

        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();
        for (Playlist playlist : playlists) {
            PlaylistReadResponseForm responseForm = new PlaylistReadResponseForm(playlist, playlist.getSongList(), playlist.getLikers().size());
            responseForms.add(responseForm);
        }

        return responseForms;
    }

    @Override
    @Transactional
    public List<PlaylistReadResponseForm> sortByLikersSlicePlaylist(int page) {

        Slice<Playlist> playlists = playlistRepository.sortByLikersSlicePlaylist(PageRequest.of(page - 1, PAGE_SIZE));

        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();
        for (Playlist playlist : playlists) {
            PlaylistReadResponseForm responseForm = new PlaylistReadResponseForm(playlist, playlist.getSongList(), playlist.getLikers().size());
            responseForms.add(responseForm);
        }

        return responseForms;
    }

    @Override
    public long countAllPlaylist() {
        return getPageCount(playlistRepository.count());
    }

    @Override
    public long countTotalPageByLoginAccount(HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

        long count = playlistRepository.countPlaylistByAccountId(account.getId());
        return getPageCount(count);
    }

    private long getPageCount(long count) {
        return (count % PAGE_SIZE == 0) ? (count / PAGE_SIZE) : (count / PAGE_SIZE + 1);
    }

    @Override
    @Transactional
    public List<PlaylistReadResponseForm> list() {
        List<Playlist> playlists = playlistRepository.findAll();

        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();
        for (Playlist playlist : playlists) {
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
    public boolean modify(PlaylistModifyRequestForm requestForm) {
        Playlist playlist = playlistRepository.findById(requestForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("플레이리스트 없음"));

        playlist.setTitle(requestForm.getTitle());

        playlistRepository.save(playlist);

        return true;
    }

    @Override
    public List<PlaylistReadResponseForm> listByLoginAccount(int page, HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

        Slice<Playlist> playlists = playlistRepository.findPlaylistByAccountId(account, PageRequest.of(page - 1, PAGE_SIZE));

        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();
        for (Playlist playlist : playlists) {
            PlaylistReadResponseForm responseForm = new PlaylistReadResponseForm(playlist, playlist.getSongList(), playlist.getLikers().size());
            responseForms.add(responseForm);
        }
        return responseForms;
    }

    @Override
    @Transactional
    public boolean delete(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("플레이리스트 없음"));

        //Account 클래스에서 removeFromLikedPlaylists 메소드 호출 - likedPlaylists 컬렉션에서 제거
        for (Account account : playlist.getLikers()) {
            account.removeFromLikedPlaylists(playlist);
        }

        //Playlist 클래스에서 removeFromLikers 메소드 호출 - likers 컬렉션에서 제거
        for (Account liker : new HashSet<>(playlist.getLikers())) {
            playlist.removeFromLikers(liker);
        }

        playlistRepository.deleteById(playlistId);
        return true;
    }

    @Override
    @Transactional
    public int likePlaylist(Long playlistId, HttpServletRequest request) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("플레이리스트 없음"));

        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));
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
    @Transactional
    public int unlikePlaylist(Long playlistId, HttpServletRequest request) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("플레이리스트 없음"));

        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

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
    @Transactional
    public Boolean isPlaylistLiked(Long playlistId, HttpServletRequest request) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("플레이리스트 없음"));

        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

        Set<Playlist> likedPlaylists = account.getLikedPlaylists();

        return likedPlaylists.contains(playlist);
    }

    @Override
    @Transactional
    public List<PlaylistReadResponseForm> likedPlaylistByLoginAccount(int page, HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

        Playlist[] playlists = account.getLikedPlaylists().toArray(new Playlist[0]);

        int start = (page - 1) * PAGE_SIZE;
        int end = start + PAGE_SIZE;

        if (end > playlists.length) {
            end = playlists.length;
        }

        List<PlaylistReadResponseForm> responseForms = new ArrayList<>();

        for (int i = start; i < end; i++) {
            Playlist playlist = playlistRepository.findByPlaylistId(playlists[i].getId())
                    .orElseThrow(() -> new IllegalArgumentException("플레이리스트 없음"));

            PlaylistReadResponseForm responseForm = new PlaylistReadResponseForm(playlist, playlist.getSongList(), playlist.getLikers().size());
            responseForms.add(responseForm);
        }
        return responseForms;
    }

    @Override
    @Transactional
    public long countLikedPlaylist(HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

        return account.getLikedPlaylists().size();
    }

    @Override
    @Transactional
    public long countPageLikedPlaylist(HttpServletRequest request) {
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 없음"));

        long count = account.getLikedPlaylists().size();

        return getPageCount(count);
    }
}