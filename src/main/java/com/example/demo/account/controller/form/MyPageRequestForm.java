package com.example.demo.account.controller.form;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MyPageRequestForm {

    private String email;

    private String name;

    private String phoneNumber;

}
