package com.example.demo.member.controller;



import com.example.demo.member.controller.form.MemberLoginRequestForm;
import com.example.demo.member.controller.form.MemberLoginResponseForm;
import com.example.demo.member.controller.form.MemberRequestForm;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    final private MemberService memberService;

    @PostMapping("/sign-up")
    public boolean signUp(@RequestBody MemberRequestForm memberRequestForm) {
        System.out.println(memberRequestForm);
        return memberService.register(memberRequestForm);

    }
    @GetMapping("/check-nickName/{nickName}")
    public boolean checkNickName(@PathVariable("nickName") String nickName) {
            return memberService.checkNickName(nickName);
    }

    @PostMapping("/login")
    public MemberLoginResponseForm memberLogin(@RequestBody MemberLoginRequestForm memberLoginRequestForm){
        return memberService.login(memberLoginRequestForm.toMemberLoginRequest());
    }


}
