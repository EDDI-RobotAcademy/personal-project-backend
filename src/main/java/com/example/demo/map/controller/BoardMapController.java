package com.example.demo.map.controller;

import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.map.controller.response.BoardMapListResponse;
import com.example.demo.map.service.BoardMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class BoardMapController {

    final private BoardMapService boardMapService;

    @GetMapping("/boardMapList")
    public List<BoardMapListResponse> boardMapList () {
        return boardMapService.list();
    }
}
