package com.example.demo.board.event.repository;

import com.example.demo.board.event.entity.EventBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventBoard, Long> {
    Optional<EventBoard> findByEventId(Long eventId);

}
