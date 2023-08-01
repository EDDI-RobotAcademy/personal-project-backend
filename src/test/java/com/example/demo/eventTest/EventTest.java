package com.example.demo.eventTest;

import com.example.demo.board.event.controller.form.EventModifyForm;
import com.example.demo.board.event.controller.form.EventRegistForm;
import com.example.demo.board.event.entity.EventBoard;
import com.example.demo.board.event.repository.EventRepository;
import com.example.demo.board.event.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventTest {
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("이벤트 게시물 생성")
    void 게시물_생성(){
        final String title = "이벤트 제목1";
        final String content = "이벤트 내용1";
        final Long accountId = 1L;
        final String image = "love.jpg";
        EventRegistForm eventRegistForm = new EventRegistForm(title,content,accountId,image);
        EventBoard eventBoard = eventService.regist(eventRegistForm.toEventBoard());
        long eventId = eventBoard.getEventId();
        EventBoard DBevent = eventRepository.findByEventId(eventId).get();

        assertEquals(title,DBevent.getTitle());
        assertEquals(content,DBevent.getContent());
        assertEquals(accountId,DBevent.getAccount().getAccountId());
        assertEquals(image,DBevent.getImage());

    }

    @Test
    @DisplayName("이벤트 게시물을 수정합니다.")
    void 게시물_수정(){
        final Long eventId = 3L;
        final String title = "변경 제목1";
        final String content = "변경 내용1";
        final String image = "change_image.jpg";
        EventModifyForm eventModifyForm = new EventModifyForm(eventId,title,content,image);
        EventBoard eventBoard = eventModifyForm.toEventBoard();
        eventService.modify(eventBoard);
        EventBoard DBevent = eventRepository.findByEventId(eventId).get();

        assertEquals(title,DBevent.getTitle());
        assertEquals(content,DBevent.getContent());
        assertEquals(image,DBevent.getImage());
    }

    @Test
    @DisplayName("이벤트 게시판 목록 확인")
    void 게시판_목록_확인 (){
        List<EventBoard> eventBoardList = eventService.list();

        for (EventBoard eventBoard: eventBoardList){
            System.out.println("===============");
            System.out.println(eventBoard.getEventId());
            System.out.println(eventBoard.getTitle());
            System.out.println(eventBoard.getDate());
            System.out.println(eventBoard.getAccount().getAccountId());
            System.out.println(eventBoard.getImage());

            assertTrue(eventBoard.getEventId() != null);
            assertTrue(eventBoard.getTitle() != null);
            assertTrue(eventBoard.getDate() != null);
            assertTrue(eventBoard.getAccount().getAccountId() != null);
            assertTrue(eventBoard.getImage() != null);
        }
    }

    @Test
    @DisplayName("이벤트 게시물 상세 정보 확인")
    void 게시물_정보_확인(){
        final Long eventId = 3L;
        EventBoard eventBoard = eventService.read(eventId);
        EventBoard DBevent = eventRepository.findByEventId(eventId).get();
        System.out.println(eventBoard.getEventId());
        System.out.println(eventBoard.getTitle());
        System.out.println(eventBoard.getContent());
        System.out.println(eventBoard.getAccount().getAccountId());
        System.out.println(eventBoard.getImage());

        assertEquals(eventBoard.getEventId(),DBevent.getEventId());
        assertEquals(eventBoard.getTitle(),DBevent.getTitle());
        assertEquals(eventBoard.getContent(),DBevent.getContent());
        assertEquals(eventBoard.getDate(),DBevent.getDate());
        assertEquals(eventBoard.getAccount().getAccountId(),DBevent.getAccount().getAccountId());
        assertEquals(eventBoard.getImage(),DBevent.getImage());
    }

    @Test
    @DisplayName("이벤트 게시물을 삭제합니다.")
    void 게시물_삭제(){
        final Long eventId = 3L;
        Boolean resultEventDelete = eventService.delete(eventId);

        assertTrue(resultEventDelete);
    }
}
