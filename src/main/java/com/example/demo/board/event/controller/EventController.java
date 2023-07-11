package com.example.demo.board.event.controller;

import com.example.demo.board.event.controller.form.EventModifyForm;
import com.example.demo.board.event.controller.form.EventRegistForm;
import com.example.demo.board.event.entity.EventBoard;
import com.example.demo.board.event.service.EventService;
import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    final EventService eventService;

    // 이벤트 게시물 등록 기능
    @PostMapping("/regist")
    public String eventRegist(EventRegistForm eventRegistForm){
        log.info("EventRegist() ");
        EventBoard eventBoard = eventService.regist(eventRegistForm.toEventBoard());
        if (eventBoard == null){
            return null;
        }

        return eventBoard.getTitle();
    }

    // 이벤트 게시판 수정
    @PutMapping("/modify")
    public Long eventModify(@RequestBody EventModifyForm eventModifyForm){
        log.info("EventModify() ");
        EventBoard eventBoard = eventService.modify(eventModifyForm.toEventBoard());

        return eventBoard.getEventId();
    }

    // 이벤트 게시물 목록 확인
    @GetMapping("/list")
    public List<EventBoard> eventList(){
        log.info("EventList() ");
        List<EventBoard> returnedEventList = eventService.list();
        log.info("EventList : " + returnedEventList);

        return returnedEventList;
    }

    // 이벤트 게시물 읽기
    @GetMapping("/list/{eventId}")
    public EventBoard eventRead(@PathVariable Long eventId){
        log.info("EventRead() ");
        EventBoard readEventBoard = eventService.read(eventId);

        return readEventBoard;
    }


    // 이벤트 게시물 삭제
    @DeleteMapping("/delete")
    public boolean eventDelete(@RequestParam("eventId") Long eventId){
        log.info("EventDelete() ");
        boolean resultDeleteEvent = eventService.delete(eventId);

        return resultDeleteEvent;
    }

}
