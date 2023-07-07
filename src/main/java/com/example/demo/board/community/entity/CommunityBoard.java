package com.example.demo.board.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class CommunityBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityNumber;
    @Setter
    private String communityTitle;
    @Setter
    private String communityContent;
    private LocalDateTime communityDate;

    public CommunityBoard(String communityTitle, String communityContent) {
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        this.communityDate = LocalDateTime.now();
    }
}
