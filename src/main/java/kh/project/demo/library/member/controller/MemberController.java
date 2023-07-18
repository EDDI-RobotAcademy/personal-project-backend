package kh.project.demo.library.member.controller;

import kh.project.demo.library.member.controller.form.request.MemberIdCheckForm;
import kh.project.demo.library.member.controller.form.request.MemberBasicForm;
import kh.project.demo.library.member.controller.form.request.MemberSignUpForm;
import kh.project.demo.library.member.controller.form.request.MemberAccountStopForm;
import kh.project.demo.library.member.controller.form.response.MemberLoginRespnseForm;
import kh.project.demo.library.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/library-member")
public class MemberController {

    final private MemberService memberService;

    // 아이디 중복 확인
    @GetMapping("/check-id/{memberId}")
    public Boolean checkId(@PathVariable("memberId") String memberId) {
        log.info("check id duplication: "+ memberId);

        return memberService.checkIdDuplication(memberId);
    }

    // 이메일 중복 확인
    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable("email") String email) {
        log.info("check email duplication: "+ email);

        return memberService.checkEmailDuplication(email);
    }

    // 회원 가입
    @PostMapping("/sign-up")
    public Boolean signUp (@RequestBody MemberSignUpForm memberSignUpForm) {
        log.info("sign Up()");

        return memberService.memberSignUp(memberSignUpForm);
    }

    // 로그인
    @PostMapping("/sign-in")
    public MemberLoginRespnseForm signIn(@RequestBody MemberBasicForm memberSignInForm) {
        log.info("sign in()");

        return memberService.memberSignIn(memberSignInForm);
    }

    // 회원 삭제
    @DeleteMapping("/member-delete")
    public boolean MemberDelete(@RequestBody MemberBasicForm memberDeleteForm) {
        log.info("member delete()");

        return memberService.memberDelete(memberDeleteForm);
    }

    // 회원 계정 정지
    @PostMapping("/member-account-stop")
    public boolean MemberAccountStop(@RequestBody MemberAccountStopForm memberAccountForm) {
        log.info("member account stop");

        return memberService.memberAccountStop(memberAccountForm);
    }

}
