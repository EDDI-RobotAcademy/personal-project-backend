package com.example.demo.user.repository;

public interface UserTokenRepository {
    void save(String userToken, Long id);
    Long findUserIdByToken(String userToken);

}
