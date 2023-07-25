package com.example.demo.user.controller;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.user.controller.form.UserRegisterForm;
import com.example.demo.user.controller.form.UserSignInRequestForm;
import com.example.demo.user.controller.form.UserSignInResponseForm;
import com.example.demo.user.controller.form.UserSignValidateForm;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.Bookmark;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor

@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @GetMapping("/login/{uid}")
    public User userLogin(@PathVariable("uid") String uid) {
        log.info(uid);
        return userService.logIn(uid);
    }
    @PostMapping("/sign-validate")
    public List<String> userValidate(@RequestBody UserSignValidateForm userSignValidateForm) {
        log.info(userSignValidateForm.getEmail());
        log.info(userSignValidateForm.getNickName());
        return userService.SignValidate(userSignValidateForm.toUserSignValidateRequest());
    }

    @PostMapping("/sign-up")
    public Boolean userSignUp(@RequestBody UserRegisterForm registerForm) {
        log.info(registerForm.getEmail());
        log.info(registerForm.getName());
        log.info(registerForm.getUid());
        return userService.signUp(registerForm.toUserSignUpRequest());
    }
    @PostMapping("/sign-in")
    public UserSignInResponseForm UserSignIn(@RequestBody UserSignInRequestForm signInRequestForm){
        return userService.signIn(signInRequestForm.toUserSignInRequest());
    }
    @DeleteMapping("/{userId}")
    public void signDown(@PathVariable("userId") Long userId) {
        log.info("signDown()");
        userService.delete(userId);
    }
    @PostMapping("/bookmark/{userId}")
    public ResponseEntity<String> addBookmark(@PathVariable Long userId, @RequestBody Long boardId) {
        log.info("ResponseEntity()");
        return ResponseEntity.ok("북마크 추가 성공");
    }

    @DeleteMapping("/bookmark/{userId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long userId, @RequestBody Long boardId) {
        return ResponseEntity.ok("북마크 삭제 성공");
    }
}