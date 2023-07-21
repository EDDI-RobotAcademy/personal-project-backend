package com.example.demo.board.form;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegisterBoardForm {
        private String title;
        private String nickname;
        private String content;
        private List<FilePaths> awsFileList;
        private String userToken;

        public MemberBoard toMemberBoard() {
            return new MemberBoard(title, nickname, content, awsFileList);
        }

}
