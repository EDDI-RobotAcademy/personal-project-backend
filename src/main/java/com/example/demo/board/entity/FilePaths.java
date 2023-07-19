package com.example.demo.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FilePaths {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Getter
    @Setter
    private String imagePath;

    @JoinColumn(name="board_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private MemberBoard memberBoard;
    public FilePaths(String imagePath){
        this.imagePath = imagePath;
    }
    public FilePaths(String imagePath, MemberBoard memberBoard) {
        this.imagePath = imagePath;
        this.memberBoard = memberBoard;
    }
}
