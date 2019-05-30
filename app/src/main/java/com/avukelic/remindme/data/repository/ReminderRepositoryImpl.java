package com.avukelic.remindme.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.data.model.Reminder;
import com.avukelic.remindme.singleton.FirebaseDatabaseSingleton;
import com.avukelic.remindme.singleton.UserSingleton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ReminderRepositoryImpl implements ReminderRepository {

    @Inject
    public ReminderRepositoryImpl() {
    }

    private MutableLiveData<List<Reminder>> reminderLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Reminder> reminderSingleLiveEvent = new SingleLiveEvent<>();

    @Override
    public LiveData<List<Reminder>> getReminders() {
        List<Reminder> reminders = new ArrayList<>();
        Query query = FirebaseDatabaseSingleton.getInstance().getReminderDatabaseReference();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    reminders.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Reminder reminder = child.getValue(Reminder.class);
                        reminders.add(reminder);
                    }
                    UserSingleton.getInstance().setLastReminderId(reminders.get(reminders.size() - 1).getId());
                    reminderLiveData.setValue(reminders);
                } else {
                    reminderLiveData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return reminderLiveData;
    }

    @Override
    public SingleLiveEvent<Reminder> addReminder(Reminder reminder) {
        DatabaseReference reminders = FirebaseDatabaseSingleton.getInstance()
                .getReminderDatabaseReference()
                .child(String.valueOf(reminder.getId()));
        reminders.keepSynced(true);
        reminders.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                reminderSingleLiveEvent.postValue(reminder);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reminders.setValue(reminder);
        return reminderSingleLiveEvent;
    }
}
