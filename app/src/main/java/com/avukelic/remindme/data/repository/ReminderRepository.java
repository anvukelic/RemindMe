package com.avukelic.remindme.data.repository;

import androidx.lifecycle.LiveData;

import com.avukelic.remindme.data.model.Reminder;

import java.util.List;

import io.reactivex.Single;

public interface ReminderRepository {

    LiveData<List<Reminder>> getReminders();

    SingleLiveEvent<Reminder> addReminder(Reminder reminder);

}
