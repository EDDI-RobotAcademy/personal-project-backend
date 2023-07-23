package com.example.demo.map.service;

import com.example.demo.map.controller.response.BoardMapListResponse;
import com.example.demo.map.entity.BoardMap;
import com.example.demo.map.repository.BoardMapRepository;
import com.example.demo.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardMapServiceImpl implements BoardMapService{

    final private BoardMapRepository boardMapRepository;

    final private JwtProvider jwtProvider;


    @Override
    public List<BoardMapListResponse> list() {
        List<BoardMap> boardMaps = boardMapRepository.findAll(Sort.by(Sort.Direction.DESC, "boardMapId"));

        return boardMaps.stream()
                .map(boardMap -> new BoardMapListResponse(boardMap.getBoardMapId(), boardMap.getTitle(), boardMap.getWriter(), boardMap.getCreatedData()))
                .collect(Collectors.toList());
    }
}
