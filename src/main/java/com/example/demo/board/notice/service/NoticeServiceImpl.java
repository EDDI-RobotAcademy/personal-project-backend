package com.example.demo.board.notice.service;

import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeNumberForm;
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

    // 공지사항 게시판 수정
    @Override
    public NoticeBoard modify(NoticeModifyForm noticeModifyForm) {

        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByNoticeNumber(noticeModifyForm.getNoticeNumber());

        if(maybeNoticeBoard.isEmpty()){
            log.info("에러 발생");
            return null;
        }
        NoticeBoard noticeBoard = maybeNoticeBoard.get();

        noticeBoard.setNoticeTitle(noticeModifyForm.getNoticeTitle());
        noticeBoard.setNoticeContent(noticeModifyForm.getNoticeContent());

        return noticeRepository.save(noticeBoard);
    }

    // 공지사항 게시물 삭제
    @Override
    public Boolean delete(Long noticeNumber) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByNoticeNumber(noticeNumber);

        if (maybeNoticeBoard.isEmpty()){
            log.info("에러 발생");
            return false;
        }
        NoticeBoard noticeBoard = maybeNoticeBoard.get();
        noticeRepository.delete(noticeBoard);

        return true;

    }

    @Override
    public NoticeBoard read(Long noticeNumber) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeRepository.findByNoticeNumber(noticeNumber);

        if (maybeNoticeBoard.isEmpty()){
            log.info("에러 발생");
            return null;
        }

        return maybeNoticeBoard.get();
    }

    @Override
    public List<NoticeBoard> list() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC,"noticeNumber"));
    }
}
