package com.example.demo.businessMember.controller.form;

import com.example.demo.businessMember.entity.BusinessMember;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BusinessMemberRegisterRequestForm {

    final private String email;
    final private String password;
    final private String businessNumber;
    final private String name;
    final private String phoneNumber;

    public BusinessMember toEntity() {
        return new BusinessMember(email, password, businessNumber, name, phoneNumber);
    }

}
