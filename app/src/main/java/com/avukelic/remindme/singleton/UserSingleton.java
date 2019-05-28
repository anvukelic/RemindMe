package com.avukelic.remindme.singleton;

import com.avukelic.remindme.data.model.User;
import com.google.firebase.auth.FirebaseUser;

public class UserSingleton {

    private static UserSingleton ourInstance;
    private User user;
    private int lastReminderId;

    public static UserSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserSingleton();
        }
        return ourInstance;
    }

    private UserSingleton() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLastReminderId() {
        return ++lastReminderId;
    }

    public void setLastReminderId(int lastReminderId) {
        this.lastReminderId = lastReminderId;
    }

    public void setInitUser(FirebaseUser user){
        this.user = new User(user.getUid(), user.getEmail());
    }
}
