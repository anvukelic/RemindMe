package com.avukelic.remindme.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.avukelic.remindme.data.model.ReminderModel;

@Entity(tableName = "userData")
public class UserDaoModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "email")
    private String email;


    @ColumnInfo(name = "reminderModel")
    private ReminderModel reminderModel;

    public UserDaoModel(@NonNull String id, String email, ReminderModel reminderModel) {
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
}
