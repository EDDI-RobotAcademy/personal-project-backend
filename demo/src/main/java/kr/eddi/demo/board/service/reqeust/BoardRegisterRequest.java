package kr.eddi.demo.board.service.reqeust;

import kr.eddi.demo.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardRegisterRequest {
    final private String boardTitle;

    final private String boardInfo;

    final private String coordLat;
    final private String coordLng;
    final private String writer;
    final private String imgPath;
    final private String boardTransport;

    public Board toBoard(){
        return new Board(boardTitle,boardInfo,coordLat,coordLng, writer,imgPath,boardTransport);}
}
