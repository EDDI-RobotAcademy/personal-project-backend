package com.example.demo.map.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.map.controller.form.BoardMapRequestForm;
import com.example.demo.map.controller.response.BoardMapListResponse;
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
import org.springframework.stereotype.Service;

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
    public List<BoardMapListResponse> list() {
        List<BoardMap> boardMaps = boardMapRepository.findAll(Sort.by(Sort.Direction.DESC, "boardMapId"));

        return boardMaps.stream()
                .map(boardMap -> new BoardMapListResponse(boardMap.getBoardMapId(), boardMap.getTitle(), boardMap.getWriter(), boardMap.getCreatedData()))
                .collect(Collectors.toList());
    }

    @Override
    public BoardMapRegisterResponse register(String accessToken, BoardMapRequestForm form) {
        SecretKey key = jwtProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(accessToken.replace(" ", "").replace("Bearer", ""));

        String email = claims.getBody().getSubject();
        email = email.substring(email.indexOf("\"email\":\"") + 9, email.indexOf("\",\"types\""));
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            form.setWriter(account.getName());
            BoardMap boardMap = new BoardMap(form.getPlaceName(), form.getTitle(), form.getWriter(), form.getContent());
            boardMap.setAccount(account);
            boardMapRepository.save(boardMap);

            BoardMapRegisterResponse response = new BoardMapRegisterResponse(
                    boardMap.getBoardMapId(), boardMap.getTitle(), boardMap.getWriter(), boardMap.getContent());
            return response;
        }
        return null;
    }
}
