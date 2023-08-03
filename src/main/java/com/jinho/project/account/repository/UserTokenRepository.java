package com.jinho.project.account.repository;


public interface UserTokenRepository {
    void save(String userToken, Long id);

    Long findAccountIdByUserToken(String userToken);
}
