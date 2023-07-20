package com.example.demo.oauth.Controller;

import com.example.demo.oauth.service.KakaoAPI;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@PropertySource("classpath:kakao.properties")
public class KakaoController {

    final private KakaoAPI kakaoApi;

    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirect_uri;
    @GetMapping("/kakao/oauth")
    public String kakaoConnect() {
        log.info("clientId : " + clientId);
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + clientId);
        url.append("&redirect_uri=" + redirect_uri);
        url.append("&response_type=code");
        System.out.println(url.toString());
        return url.toString();

    }

    @RequestMapping(value="/login/oauth2/code/kakao")
    public RedirectView kakaoCallback(@RequestParam String code, HttpSession session) {
        System.out.println("kakao callback 컨트롤러 접근");
        System.out.println(code);

        RedirectView redirectView = new RedirectView();

        //1. 코드전달
        String access_token = kakaoApi.getAccessToken(code);

        System.out.println("1. access_token : " + access_token);



        //2. 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(access_token);

        System.out.println("2. login info : " + userInfo);
        System.out.println("2-1. login info : " + userInfo.toString());

        if(userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_token", access_token);
        }

        redirectView.addStaticAttribute("email", userInfo.get("email"));
        redirectView.setUrl("http://localhost:8080");

        return redirectView;
    }
}



