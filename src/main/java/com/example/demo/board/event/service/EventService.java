package com.example.demo.board.event.service;

import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.event.entity.EventBoard;

import java.util.List;

public interface EventService {

    List<EventBoard> list();

    EventBoard regist(EventBoard eventBoard);

    EventBoard modify(EventBoard eventBoard);

    Boolean delete(Long eventId);

    EventBoard read(Long eventId);
}
