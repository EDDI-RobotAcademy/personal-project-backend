package com.example.demo.user.controller;

import com.example.demo.user.controller.form.UserRegisterForm;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor

@RequestMapping("/user")
public class UserController {
    final private UserService userService;
    @GetMapping("check-email/{email}")
    public Boolean checkEmail(@PathVariable("email")String email) {
        log.info("check-email duplication" + email);

        return userService.checkEmailDuplication(email);
    }
    @PostMapping("sign-up")
    public Boolean signUp(@RequestBody UserRegisterForm form) {
        log.info("signUp(): " + form);

        return userService.signUp(form.toUserRegisterRequest());
    }
}
