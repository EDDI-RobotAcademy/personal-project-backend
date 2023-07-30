package com.example.demo.account.controller.form.request;

import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.request.NormalAccountRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NormalAccountRegisterForm {

    private String email;
    private String password;
    private RoleType roleType;
    private String userName;
    private String gender;
    private String birth;
    private String nickName;
    private String address;

    public NormalAccountRegisterRequest toAccountRegisterRequest () {

        return new NormalAccountRegisterRequest(
                email, password, roleType, userName, gender, birth, nickName, address);
    }
}
