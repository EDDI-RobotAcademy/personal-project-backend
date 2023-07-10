package com.example.demo.board.community.controller.form;


import com.example.demo.board.community.entity.CommunityBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityNumberForm {

    private Long communityNumber;

    public CommunityNumberForm(Long communityNumber) {
        this.communityNumber = communityNumber;
    }

}

