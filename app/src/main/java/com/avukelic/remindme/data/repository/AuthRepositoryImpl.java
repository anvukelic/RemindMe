package com.avukelic.remindme.data.repository;

import com.avukelic.remindme.data.model.User;
import com.avukelic.remindme.singleton.UserSingleton;

import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {

    @Inject
    public AuthRepositoryImpl() {

    }

    @Override
    public void saveUser(User user) {
        UserSingleton.getInstance().setUser(user);
    }
}
