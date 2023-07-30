package kr.eddi.demo.board.entity;

import jakarta.persistence.*;
import kr.eddi.demo.comment.entity.Comment;
import kr.eddi.demo.marker.entity.Marker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardTitle;
    private String boardInfo;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marker_id")
    private Marker marker;



    private String writer;
    private String imgPath;
    private String boardTransport;


    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int comments;


    public Board(String boardTitle, String boardInfo, String coordLat, String coordLng, String writer,String imgPath, String boardTransport) {
        this.boardTitle = boardTitle;
        this.boardInfo = boardInfo;
        this.marker = new Marker(coordLat, coordLng);
        this.writer = writer;
        this.imgPath=imgPath;
        this.boardTransport=boardTransport;


    }


    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;
}
