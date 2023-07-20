package com.example.demo.board.community.service;

import com.example.demo.board.community.controller.form.CommunityModifyForm;
import com.example.demo.board.community.controller.form.CommunityRegistForm;
import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.notice.controller.form.NoticeModifyForm;
import com.example.demo.board.notice.controller.form.NoticeRegistForm;
import com.example.demo.board.notice.entity.NoticeBoard;

import java.util.List;

public interface CommunityServcie {

    List<CommunityBoard> list();

    CommunityBoard regist(CommunityBoard communityBoard);

    CommunityBoard modify(CommunityBoard communityBoard);

    Boolean delete(String communityId);

    CommunityBoard read(String communityId);
}
