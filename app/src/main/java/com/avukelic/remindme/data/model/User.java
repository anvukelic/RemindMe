package com.avukelic.remindme.data.model;

import com.avukelic.remindme.database.DaoMapper;
import com.avukelic.remindme.database.UserDaoModel;

public class User implements DaoMapper<UserDaoModel> {

    private String id;
    private String email;
    private ReminderModel reminderModel;

    public User(String id, String email, ReminderModel reminderModel) {
        this.id = id;
        this.email = email;
        this.reminderModel = reminderModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ReminderModel getReminderModel() {
        return reminderModel;
    }

    public void setReminderModel(ReminderModel reminderModel) {
        this.reminderModel = reminderModel;
    }

    @Override
    public UserDaoModel mapToDao() {
        return new UserDaoModel(id, email, reminderModel);
    }
}
