package kr.eddi.demo.board.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardModifyRequest {
    final private String boardTitle;
    final private String boardInfo;
}
