package com.example.demo.oauth.service;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.member.controller.form.MemberLoginResponseForm;
import com.example.demo.member.service.MemberService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Slf4j
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:kakao.properties")
public class KakaoAPI {

    final private MemberService memberService;

    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirect_uri;
    @Value("${kakao.token-uri}")
    private String token_uri;

    public String getAccessToken(String code) {
    String accessToken = "";
    String refreshToken = "";

		try {
        URL url = new URL(token_uri);
        // 해당 주소의 페이지로 접속
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        //InputStream으로 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의한다.
        conn.setDoOutput(true);

        //writer: 출력, reader: 입력
        //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb= new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=" + clientId);
        sb.append("&redirect_url=" + redirect_uri);
        sb.append("&code=" + code);

        bw.write(sb.toString());
        // 그리고 스트림의 버퍼를 비워준다.
        bw.flush();

        int responsCode = conn.getResponseCode();
        System.out.println("3. responsCode = " + responsCode);

        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";
        //파일의 한줄한줄을 읽어서 출력한다.
        while((line = br.readLine())!=null) {
            result += line;
        }
        System.out.println("resopnsebody = " + result);

        //json 형식으로 파시변환
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        accessToken = element.getAsJsonObject().get("access_token").getAsString();
        refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

        // 스트림을 닫는다.
        br.close();
        bw.close();

    }catch(Exception e) {
        e.printStackTrace();
    }

		return accessToken;
}

    public HashMap<String, Object> getUserInfo(String access_token) {

        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";



        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);


            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine())!=null) {
                result += line;
            }

            System.out.println("resopnse body =" + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String email = "";
            boolean has_email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            if(has_email) {
                try{
                    email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
                }catch(NullPointerException e){
                    email = "kk" + element.getAsJsonObject().get("id").getAsString();;
                }
            }
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            String userToken = memberService.signUpKakao(email, nickname).getUserToken();

            userInfo.put("nickname", nickname);
            userInfo.put("userToken", userToken);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }


}

