package com.example.demo.map.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.map.controller.form.BoardMapRequestForm;
import com.example.demo.map.controller.response.BoardMapListResponse;
import com.example.demo.map.controller.response.BoardMapReadResponse;
import com.example.demo.map.controller.response.BoardMapRegisterResponse;
import com.example.demo.map.entity.BoardMap;
import com.example.demo.map.repository.BoardMapRepository;
import com.example.demo.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardMapServiceImpl implements BoardMapService{

    final private BoardMapRepository boardMapRepository;
    final private AccountRepository accountRepository;
    final private JwtProvider jwtProvider;


    @Override
    public List<BoardMapListResponse> list(String placeName) {
        List<BoardMap> boardMaps = boardMapRepository.findAll(Sort.by(Sort.Direction.DESC, "boardMapId"));

        return boardMaps.stream()
                .filter(boardMap -> boardMap.getPlaceName().equals(placeName)) 
                .map(boardMap -> new BoardMapListResponse(boardMap.getBoardMapId(), boardMap.getPlaceName(), boardMap.getTitle(), boardMap.getWriter(), boardMap.getCreatedData()))
                .collect(Collectors.toList());
    }

    @Override
    public BoardMapRegisterResponse register(String accessToken, BoardMapRequestForm form, String placeName) {
        SecretKey key = jwtProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(accessToken.replace(" ", "").replace("Bearer", ""));

        String email = claims.getBody().getSubject();
        email = email.substring(email.indexOf("\"email\":\"") + 9, email.indexOf("\",\"types\""));
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            form.setWriter(account.getName());
            BoardMap boardMap = new BoardMap(placeName, form.getTitle(), form.getWriter(), form.getContent());
            boardMap.setAccount(account);
            boardMapRepository.save(boardMap);

            BoardMapRegisterResponse response = new BoardMapRegisterResponse(
                    boardMap.getBoardMapId(), placeName, boardMap.getTitle(), boardMap.getWriter(), boardMap.getContent());
            return response;
        }
        return null;
    }

    @Override
    public BoardMapReadResponse read(String placeName, Long boardMapId, String accessToken) {
        SecretKey key = jwtProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(accessToken.replace(" ", "").replace("Bearer", ""));

        String email = claims.getBody().getSubject();
        email = email.substring(email.indexOf("\"email\":\"") + 9, email.indexOf("\",\"types\""));
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        maybeAccount.get();
        Optional<BoardMap> maybeBoardMap = boardMapRepository.findById(boardMapId);
        if (maybeBoardMap.isEmpty()) {
            return null;
        }
        BoardMap boardMap = maybeBoardMap.get();
        BoardMapReadResponse response = new BoardMapReadResponse(
                boardMap.getAccount().getId(), boardMap.getBoardMapId(), boardMap.getPlaceName(), boardMap.getTitle(), boardMap.getWriter(), boardMap.getContent(), boardMap.getCreatedData());
        return response;
    }

    @Override
    public void delete(Long boardMapId, String placeName, String accessToken) throws RuntimeException {
        SecretKey key = jwtProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(accessToken.replace(" ", "").replace("Bearer", ""));

        String email = claims.getBody().getSubject();
        email = email.substring(email.indexOf("\"email\":\"") + 9, email.indexOf("\",\"types\""));
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        Long accountId = maybeAccount.get().getId();

        Optional<BoardMap> maybeBoardMap = boardMapRepository.findById(boardMapId);
        if (maybeBoardMap.isPresent()) {
            BoardMap boardMap = maybeBoardMap.get();

            if (boardMap.getAccount() != null && boardMap.getAccount().getId().equals(accountId)) {
                log.info("권한확인: " + boardMap.getAccount().getId().equals(accountId));
                boardMapRepository.deleteById(boardMapId);
            } else {
                throw new RuntimeException("삭제 권한이 없습니다.");
            }
        } else {
            throw new RuntimeException("삭제할 게시물을 찾을 수 없습니다.");
        }
    }

    @Override
    public BoardMapRegisterResponse modify(Long boardMapId, String placeName, String accessToken, BoardMapRequestForm form) {
        SecretKey key = jwtProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(accessToken.replace(" ", "").replace("Bearer", ""));

        String email = claims.getBody().getSubject();
        email = email.substring(email.indexOf("\"email\":\"") + 9, email.indexOf("\",\"types\""));
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        Long accountId = maybeAccount.get().getId();

        Optional<BoardMap> maybeBoardMap = boardMapRepository.findById(boardMapId);
        if (maybeBoardMap.isPresent()) {
            BoardMap boardMap = maybeBoardMap.get();

            if (boardMap.getAccount() != null && boardMap.getAccount().getId().equals(accountId)) {
                log.info("권한확인: " + boardMap.getAccount().getId().equals(accountId));

                boardMap.setTitle(form.getTitle());
                boardMap.setContent(form.getContent());
                boardMapRepository.save(boardMap);

                BoardMapRegisterResponse response = new BoardMapRegisterResponse(boardMap.getBoardMapId(), boardMap.getPlaceName(), boardMap.getTitle(), boardMap.getWriter(), boardMap.getContent());
                return response;
            }
        }
        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }
}
