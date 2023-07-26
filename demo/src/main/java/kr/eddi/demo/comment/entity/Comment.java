
package kr.eddi.demo.comment.entity;


import jakarta.persistence.*;
import kr.eddi.demo.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
@Getter
@Entity
@Setter
@NoArgsConstructor
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Comment( String content, String author, Board board) {;
        this.content = content;
        this.author = author;
        this.board = board;
    }

    @CreationTimestamp
    private LocalDateTime createDate;

}