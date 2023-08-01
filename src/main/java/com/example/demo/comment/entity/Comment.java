package com.example.demo.comment.entity;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Setter
    @Column(nullable = false)
    private String text;

    @CreationTimestamp
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id")
    private MemberBoard memberBoard;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(String text, MemberBoard memberBoard, Member member) {
        this.text = text;
        this.memberBoard = memberBoard;
        this.member = member;
    }
}
