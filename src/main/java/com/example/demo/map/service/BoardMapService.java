package com.example.demo.map.service;

import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.map.controller.form.BoardMapRequestForm;
import com.example.demo.map.controller.response.BoardMapListResponse;
import com.example.demo.map.controller.response.BoardMapRegisterResponse;

import java.util.List;

public interface BoardMapService {

    List<BoardMapListResponse> list();

    BoardMapRegisterResponse register(String accessToken, BoardMapRequestForm form);
}
