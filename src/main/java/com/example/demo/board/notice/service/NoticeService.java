package com.example.demo.board.notice.service;

import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;

public interface NoticeService {

    NoticeBoard regist(NoticeRegistForm noticeRegistForm);
}
