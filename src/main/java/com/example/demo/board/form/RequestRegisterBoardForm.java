package com.example.demo.board.form;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class RequestRegisterBoardForm {

        final private String title;
        final private String nickname;
        final private String content;
        final private List<FilePaths> awsFileList;

        public MemberBoard toMemberBoard() {
            return new MemberBoard(title, nickname, content, awsFileList);
        }

}
