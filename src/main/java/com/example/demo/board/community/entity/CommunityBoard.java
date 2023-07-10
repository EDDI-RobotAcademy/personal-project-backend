package com.example.demo.board.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime communityDate;

    public CommunityBoard(String communityTitle, String communityContent) {
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
    }
}
