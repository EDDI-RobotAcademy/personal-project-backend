package kr.eddi.demo.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String title;
    private String writer;
    @Setter
    private String content;
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime createDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_board_list_id")
    private StockBoardList stockBoardList;

    public Board(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

}