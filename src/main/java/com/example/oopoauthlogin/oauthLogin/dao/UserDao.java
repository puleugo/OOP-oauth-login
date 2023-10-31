package com.example.oopoauthlogin.oauthLogin.dao;


import com.example.oopoauthlogin.oauthLogin.domain.User;

import java.util.Optional;

public class UserDao {

    UserDao() {}

    public Optional<User> findByUserProfileId(String id) {
        return Optional.empty();
    }

    public void save(User user) {
    }
}
