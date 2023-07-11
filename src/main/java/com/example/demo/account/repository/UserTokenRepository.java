package com.example.demo.account.repository;

public interface UserTokenRepository {
    void save(String userToken, Long id);
    Long findAccountIdByUserToken(String userToken);
}
