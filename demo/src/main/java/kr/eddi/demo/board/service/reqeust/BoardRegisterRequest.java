package kr.eddi.demo.board.service.reqeust;

import kr.eddi.demo.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardRegisterRequest {
    final private String imgPath;

    final private String textArea;

    final private String coordLat;
    final private String coordLng;

    public Board toBoard(){
        return new Board(imgPath,textArea,coordLat,coordLng);}
}
