package com.example.demo.board.notice.service;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    final NoticeRepository noticeRepository;

    @Override
    public NoticeBoard regist(NoticeRegistForm noticeRegistForm) {
        NoticeBoard noticeBoard = noticeRegistForm.toNoticeBoard();

        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByNoticeTitle(noticeBoard.getNoticeTitle());
        if(maybeNoticeBoard.isPresent()){
            return null;
        }

        NoticeBoard saveNoticeBoard = noticeRepository.save(noticeBoard);

        return saveNoticeBoard;
    }

    @Override
    public List<NoticeBoard> list() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC,"noticeNumber"));
    }
}
