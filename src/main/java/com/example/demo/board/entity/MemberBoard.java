package com.example.demo.board.entity;

import com.example.demo.comment.entity.Comment;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MemberBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @Setter
    private String title;
    private String nickname;
    @Setter
    private String content;
    @Setter
    private String cafeTitle;

    @CreationTimestamp
    private LocalDate createDate;

    @UpdateTimestamp
    private LocalDate updateDate;

    @Setter
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int views;

    @OneToMany(mappedBy = "memberBoard", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private List<FilePaths> filePathList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @OneToMany(mappedBy = "memberBoard", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Comment> commentList;
    public MemberBoard(String title, String nickname, String content, String cafeTitle, List<FilePaths> filePathList) {
        this.title = title;
        this.nickname = nickname;
        this.content = content;
        this.filePathList = filePathList;
        this.cafeTitle = cafeTitle;
    }

    public MemberBoard(String title, String nickname, String content, String cafeTitle, Member member) {
        this.title = title;
        this.nickname = nickname;
        this.content = content;
        this.member = member;
        this.cafeTitle = cafeTitle;
    }

    public MemberBoard(Long boardId, String title) {
        this.boardId = boardId;
        this.title = title;
    }
}
