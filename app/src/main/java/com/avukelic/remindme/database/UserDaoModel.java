package com.avukelic.remindme.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.avukelic.remindme.model.ReminderModel;

@Entity(tableName = "userData")
public class UserDaoModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "username")
    private String username;


    @ColumnInfo(name = "reminderModel")
    private ReminderModel reminderModel;

    public UserDaoModel(@NonNull String id, String username, ReminderModel reminderModel) {
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
}
