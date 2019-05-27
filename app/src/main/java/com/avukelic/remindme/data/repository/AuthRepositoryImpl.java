package com.avukelic.remindme.data.repository;

import com.avukelic.remindme.data.model.User;
import com.avukelic.remindme.database.UserDao;
import com.avukelic.remindme.database.UserDaoModel;

import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {

    private UserDao userDao;

    @Inject
    public AuthRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser() {
        return userDao.getUser();
    }

    @Override
    public void saveUser(UserDaoModel user) {
        userDao.saveUser(user);
    }
}
