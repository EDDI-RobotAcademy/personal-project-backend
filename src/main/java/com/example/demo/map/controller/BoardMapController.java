package com.example.demo.map.controller;

import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.map.controller.form.BoardMapRequestForm;
import com.example.demo.map.controller.response.BoardMapListResponse;
import com.example.demo.map.controller.response.BoardMapRegisterResponse;
import com.example.demo.map.service.BoardMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/boardMapRegister")
    public BoardMapRegisterResponse boardMapRegister(@RequestBody BoardMapRequestForm form, @RequestHeader("Authorization") String accessToken) {
        return boardMapService.register(accessToken, form);
    }
}
