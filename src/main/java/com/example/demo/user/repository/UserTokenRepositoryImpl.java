package com.example.demo.user.repository;

import java.util.HashMap;
import java.util.Map;

public class UserTokenRepositoryImpl implements UserTokenRepository {
    private static final UserTokenRepository INSTANCE = new UserTokenRepositoryImpl();
    private final Map<String, Long>userTokenMap = new HashMap<>();

    public static UserTokenRepository getInstance () {
        return INSTANCE;
    }

    @Override
    public void save(String userToken, Long id) {
        userTokenMap.put(userToken, id);
    }

    @Override
    public Long findUserIdByToken(String userToken) {
        return userTokenMap.get(userToken);
    }
}
