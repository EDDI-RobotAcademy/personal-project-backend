package com.example.demo.map.controller;

import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.map.controller.form.BoardMapRequestForm;
import com.example.demo.map.controller.response.BoardMapListResponse;
import com.example.demo.map.controller.response.BoardMapReadResponse;
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
        return boardMapService.list(placeName);
    }

    @PostMapping("/boardMapRegister/{placeName}")
    public BoardMapRegisterResponse boardMapRegister(@RequestBody BoardMapRequestForm form, @RequestHeader("Authorization") String accessToken,
                                                     @PathVariable("placeName") String placeName) {
        return boardMapService.register(accessToken, form, placeName);
    }

    @GetMapping("/{placeName}/{boardMapId}")
    public BoardMapReadResponse boardMapRead(@PathVariable("boardMapId") Long boardMapId,
                                             @PathVariable("placeName") String placeName, @RequestHeader("Authorization") String accessToken) {
        return boardMapService.read(placeName, boardMapId, accessToken);
    }

    @DeleteMapping("/{placeName}/{boardMapId}")
    public void deleteBoardMap (@PathVariable("boardMapId") Long boardMapId,
                                @PathVariable("placeName") String placeName, @RequestHeader("Authorization") String accessToken) {
        boardMapService.delete(boardMapId, placeName, accessToken);
    }

    @PutMapping("/{placeName}/{boardMapId}")
    public BoardMapRegisterResponse boardMapModify(@PathVariable("boardMapId") Long boardMapId, @PathVariable("placeName") String placeName,
                                                   @RequestHeader("Authorization") String accessToken, @RequestBody BoardMapRequestForm form) {
        return boardMapService.modify(boardMapId, placeName, accessToken, form);
    }
}
