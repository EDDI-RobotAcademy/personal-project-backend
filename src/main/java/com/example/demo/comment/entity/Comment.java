package com.example.demo.comment.entity;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(nullable = false)
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private String createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @LastModifiedDate
    private String modifiedDate;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private MemberBoard memberBoard;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
