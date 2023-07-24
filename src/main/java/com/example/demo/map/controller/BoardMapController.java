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

    @GetMapping("/boardMapList/{placeName}")
    public List<BoardMapListResponse> boardMapList (@PathVariable("placeName") String placeName) {
        log.info("실행");
        return boardMapService.list(placeName);
    }

    @PostMapping("/boardMapRegister/{placeName}")
    public BoardMapRegisterResponse boardMapRegister(@RequestBody BoardMapRequestForm form, @RequestHeader("Authorization") String accessToken,
                                                     @PathVariable("placeName") String placeName) {
        log.info("등록");
        log.info("form:" + form);
        log.info("placeName:" + placeName);
        return boardMapService.register(accessToken, form, placeName);
    }
}
