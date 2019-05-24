package com.avukelic.remindme.model;

import com.avukelic.remindme.database.DaoMapper;
import com.avukelic.remindme.database.UserDaoModel;

public class User implements DaoMapper<UserDaoModel> {

    private String id;
    private String username;
    private ReminderModel reminderModel;

    public User(String id, String username, ReminderModel reminderModel) {
        this.id = id;
        this.username = username;
        this.reminderModel = reminderModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ReminderModel getReminderModel() {
        return reminderModel;
    }

    public void setReminderModel(ReminderModel reminderModel) {
        this.reminderModel = reminderModel;
    }

    @Override
    public UserDaoModel mapToDao() {
        return new UserDaoModel(id, username, reminderModel);
    }
}
