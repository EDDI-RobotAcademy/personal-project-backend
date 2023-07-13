package com.example.demo.user.controller;

import com.example.demo.user.controller.form.UserRegisterForm;
import com.example.demo.user.controller.form.UserSignInRequestForm;
import com.example.demo.user.controller.form.UserSignInResponseForm;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor

@RequestMapping("/user")
public class UserController {
    final private UserService userService;
    @PostMapping("/sign-up")
    public Boolean userSignUp(@RequestBody UserRegisterForm registerForm) {
        return userService.signUp(registerForm.toUserSignUpRequest());
    }
    @PostMapping("sign-in")
    public UserSignInResponseForm UserSignIn(@RequestBody UserSignInRequestForm signInRequestForm){
        return userService.signIn(signInRequestForm.toUserSignInRequest());
    }

}


