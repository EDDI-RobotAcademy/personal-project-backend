package kr.eddi.demo.board.controller.form;

import kr.eddi.demo.board.service.reqeust.BoardRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardRegisterRequestForm {
    final private String boardTitle;
    final private String boardInfo;
    final private String coordLat;
    final private String coordLng;
    final private String writer;

    public BoardRegisterRequest toBoardRequest() {
        return new BoardRegisterRequest(boardTitle, boardInfo, coordLat, coordLng, writer);
    }
}