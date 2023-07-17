package kr.eddi.demo.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String boardTitle;

    String boardInfo;

    String coordLat;
    String coordLng;
    String writer;
    String totalAddress;


    public Board(String boardTitle, String boardInfo, String coordLat, String coordLng, String writer, String totalAddress) {
        this.boardTitle = boardTitle;
        this.boardInfo = boardInfo;
        this.coordLat = coordLat;
        this.coordLng = coordLng;
        this.writer = writer;
        this.totalAddress= totalAddress;
    }

    @CreationTimestamp
    private LocalDateTime createDate;//만든 시간 찍이도록 함

    @UpdateTimestamp
    private LocalDateTime updateDate;

}
