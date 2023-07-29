package com.example.demo.member.controller;



import com.example.demo.member.controller.form.MemberLoginRequestForm;
import com.example.demo.member.controller.form.MemberLoginResponseForm;
import com.example.demo.member.controller.form.MemberRequestForm;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
    @GetMapping("/check-nickName/{nickname}")
    public boolean checkNickname(@PathVariable("nickname") String nickname) {
            return memberService.checkNickname(nickname);
    }
    @GetMapping("/check-email/{email}")
    public boolean checkEmail(@PathVariable("email") String email) {
        return memberService.checkEmail(email);
    }

    @PostMapping("/login")
    public MemberLoginResponseForm memberLogin(@RequestBody MemberLoginRequestForm memberLoginRequestForm){
        return memberService.login(memberLoginRequestForm.toMemberLoginRequest());
    }

    @GetMapping(value = "/check/{memberId}")
    public Boolean checkMember(@PathVariable("memberId") Long memberId, @RequestHeader HttpHeaders headers){
        return memberService.checkMember(memberId, headers);
    }


}
