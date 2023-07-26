package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Value("${mail.userId}")
    private String userId;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.starttls.enable}")
    private String starttls_enable;

    @Value("${mail.debug}")
    private String debug;

    @Value("${mail.smtp.ssl.trust}")
    private String trust;

    @Value("${mail.smtp.ssl.enable}")
    private String ssl_enable;

        @Bean
        public JavaMailSender javaMailService() {
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

            javaMailSender.setHost(host); // 메인 도메인 서버 주소 => 정확히는 smtp 서버 주소
            javaMailSender.setUsername(userId); // 네이버 아이디
            javaMailSender.setPassword(password); // 네이버 비밀번호

            javaMailSender.setPort(port); // 메일 인증서버 포트

            javaMailSender.setJavaMailProperties(getMailProperties()); // 메일 인증서버 정보 가져오기

            return javaMailSender;
        }

        private Properties getMailProperties() {
            Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", protocol); // 프로토콜 설정
            properties.setProperty("mail.smtp.auth", auth); // smtp 인증
            properties.setProperty("mail.smtp.starttls.enable", starttls_enable); // smtp strattles 사용
            properties.setProperty("mail.debug", debug); // 디버그 사용
            properties.setProperty("mail.smtp.ssl.trust",trust); // ssl 인증 서버는 smtp.naver.com
            properties.setProperty("mail.smtp.ssl.enable",ssl_enable); // ssl 사용
            return properties;
        }
}
