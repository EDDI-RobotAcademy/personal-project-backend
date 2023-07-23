package com.example.demo.map.service;

import com.example.demo.board.controller.response.BoardListResponse;
import com.example.demo.map.controller.response.BoardMapListResponse;

import java.util.List;

public interface BoardMapService {
    List<BoardMapListResponse> list();

}
