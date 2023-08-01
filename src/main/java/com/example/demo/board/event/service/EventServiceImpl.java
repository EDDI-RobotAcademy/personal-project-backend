package com.example.demo.board.event.service;

import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.community.repository.CommunityRepository;
import com.example.demo.board.event.entity.EventBoard;
import com.example.demo.board.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    final EventRepository eventRepository;

    // 이벤트 게시물 등록 기능
    @Override
    public EventBoard regist(EventBoard eventBoard) {
        Optional<EventBoard> maybeEventBoard = eventRepository.findByEventId(eventBoard.getEventId());
        if(maybeEventBoard.isPresent()){
            return null;
        }
        EventBoard saveEventBoard = eventRepository.save(eventBoard);

        return saveEventBoard;
    }

    // 이벤트 게시판 수정
    @Override
    public EventBoard modify(EventBoard eventBoard) {
        Optional<EventBoard> maybeEventBoard = eventRepository.findByEventId(eventBoard.getEventId());
        if (maybeEventBoard.isEmpty()) {
            log.info("에러 발생");
            return null;
        }
        EventBoard getEventBoard = maybeEventBoard.get();
        getEventBoard.setTitle(eventBoard.getTitle());
        getEventBoard.setContent(eventBoard.getContent());
        getEventBoard.setImage(eventBoard.getImage());

        return eventRepository.save(getEventBoard);
    }

    // 이벤트 게시판 삭제
    @Override
    public Boolean delete(Long eventId) {
        Optional<EventBoard> maybeEventBoard = eventRepository.findByEventId(eventId);
        if (maybeEventBoard.isEmpty()){
            log.info("에러 발생");
            return false;
        }
        EventBoard eventBoard = maybeEventBoard.get();
        eventRepository.delete(eventBoard);

        return true;
    }

    // 이벤트 게시판 상세 정보
    @Override
    public EventBoard read(Long eventId) {
        Optional<EventBoard> maybeEventBoard = eventRepository.findByEventId(eventId);
        if (maybeEventBoard.isEmpty()){
            log.info("에러 발생");
            return null;
        }

        return maybeEventBoard.get();
    }

    // 이벤트 전체 게시판 목록
    @Override
    public List<EventBoard> list() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC,"eventId"));
    }
}
