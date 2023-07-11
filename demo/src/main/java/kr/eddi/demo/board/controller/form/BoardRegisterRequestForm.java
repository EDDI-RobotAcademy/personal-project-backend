package kr.eddi.demo.board.controller.form;

import kr.eddi.demo.board.service.reqeust.BoardRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardRegisterRequestForm {
    final private String imgPath;

    final private String textArea;

    final private String coordLat;
    final private String coordLng;

    public BoardRegisterRequest toBoardRequest(){
        return new BoardRegisterRequest(imgPath,textArea,coordLat,coordLng);
    }
}
