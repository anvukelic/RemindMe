package com.avukelic.remindme.database;

import androidx.room.TypeConverter;

import com.avukelic.remindme.model.Reminder;
import com.avukelic.remindme.model.Reminder.Priority;
import com.avukelic.remindme.model.ReminderModel;
import com.google.gson.Gson;

import java.io.Serializable;

public class DataConverter implements Serializable {

    @TypeConverter
    public String fromPriorityEnum(Priority priority) {
        if (priority == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(priority, Priority.class);
    }

    @TypeConverter
    public Priority toPriorityEnum(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, Priority.class);
    }

    @TypeConverter
    public String fromReminderModel(ReminderModel model) {
        if (model == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(model, ReminderModel.class);
    }

    @TypeConverter
    public ReminderModel toReminderModel(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, ReminderModel.class);
    }
}