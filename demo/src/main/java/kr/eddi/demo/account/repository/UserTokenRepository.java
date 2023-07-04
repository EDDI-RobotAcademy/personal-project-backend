package kr.eddi.demo.account.repository;

public interface UserTokenRepository {
    void save(String userToken, Long id);

    Long findAccountIdByUserToken(String userToken);
}
