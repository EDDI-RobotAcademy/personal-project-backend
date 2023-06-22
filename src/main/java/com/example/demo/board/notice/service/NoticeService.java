package com.example.demo.board.notice.service;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;

import java.util.List;

public interface NoticeService {

    List<NoticeBoard> list();

    NoticeBoard regist(NoticeRegistForm noticeRegistForm);
}
