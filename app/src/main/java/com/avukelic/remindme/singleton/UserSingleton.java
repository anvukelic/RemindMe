package com.avukelic.remindme.singleton;

import com.avukelic.remindme.database.UserDaoModel;

public class UserSingleton {

    private static UserSingleton ourInstance;
    private UserDaoModel user;

    public static UserSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserSingleton();
        }
        return ourInstance;
    }

    private UserSingleton() {
    }

    public UserDaoModel getUser() {
        return user;
    }

    public void setUser(UserDaoModel user) {
        this.user = user;
    }
}
