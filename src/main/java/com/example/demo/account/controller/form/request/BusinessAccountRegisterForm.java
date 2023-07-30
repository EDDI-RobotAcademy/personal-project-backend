package com.example.demo.account.controller.form.request;

import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAccountRegisterForm {

    private String email;
    private String password;
    private RoleType roleType;
    private String businessNumber; //사업자 등록번호
    private String userName;
    private String gender;
    private String birth;
    private String nickName;
    private String address;

    public BusinessAccountRegisterRequest toAccountRegisterRequest () {

        return new BusinessAccountRegisterRequest(
                email, password, roleType, businessNumber, userName, gender, birth, nickName, address);
    }
}
