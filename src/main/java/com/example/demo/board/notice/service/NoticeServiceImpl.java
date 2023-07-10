package com.example.demo.board.notice.service;

import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;
import com.example.demo.board.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    final NoticeRepository noticeRepository;

    // 공지사항 게시물 등록 기능
    @Override
    public NoticeBoard regist(NoticeBoard noticeBoard) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByTitle(noticeBoard.getTitle());
        if(maybeNoticeBoard.isPresent()){
            return null;
        }
        NoticeBoard saveNoticeBoard = noticeRepository.save(noticeBoard);

        return saveNoticeBoard;
    }

    // 공지사항 게시판 수정
    @Override
    public NoticeBoard modify(NoticeBoard noticeBoard) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByNoticeId(noticeBoard.getNoticeId());
        if(maybeNoticeBoard.isEmpty()){
            log.info("에러 발생");
            return null;
        }
        NoticeBoard getNoticeBoard = maybeNoticeBoard.get();

        getNoticeBoard.setTitle(noticeBoard.getTitle());
        getNoticeBoard.setContent(noticeBoard.getContent());

        return noticeRepository.save(getNoticeBoard);
    }

    // 공지사항 게시물 목록 확인
    @Override
    public List<NoticeBoard> list() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC,"noticeId"));
    }

    // 게시물 읽기
    @Override
    public NoticeBoard read(Long noticeId) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByNoticeId(noticeId);
        if (maybeNoticeBoard.isEmpty()){
            log.info("에러 발생");
            return null;
        }

        return maybeNoticeBoard.get();
    }

    // 공지사항 게시물 삭제
    @Override
    public Boolean delete(Long noticeId) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByNoticeId(noticeId);
        if (maybeNoticeBoard.isEmpty()){
            log.info("에러 발생");
            return false;
        }
        NoticeBoard noticeBoard = maybeNoticeBoard.get();
        noticeRepository.delete(noticeBoard);
        return true;
    }
}
