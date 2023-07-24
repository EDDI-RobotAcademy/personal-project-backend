package com.example.demo.map.service;

import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.map.controller.form.BoardMapRequestForm;
import com.example.demo.map.controller.response.BoardMapListResponse;
import com.example.demo.map.controller.response.BoardMapReadResponse;
import com.example.demo.map.controller.response.BoardMapRegisterResponse;

import java.util.List;

public interface BoardMapService {

    List<BoardMapListResponse> list(String placeName);

    BoardMapRegisterResponse register(String accessToken, BoardMapRequestForm form, String placeName);

    BoardMapReadResponse read(String placeName, Long boardMapId, String accessToken);

    void delete(Long boardMapId, String placeName, String accessToken);
}
