package com.avukelic.remindme.model;

import java.util.ArrayList;
import java.util.List;

public class ReminderModel {

    private List<Reminder> reminders = new ArrayList<>();

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
