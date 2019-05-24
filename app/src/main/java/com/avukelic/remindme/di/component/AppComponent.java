package com.avukelic.remindme.di.component;

import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.di.module.RoomModule;
import com.avukelic.remindme.view.MainActivity;
import com.avukelic.remindme.view.reminders.AddNewReminderActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RoomModule.class})
public interface AppComponent {

    void inject(RemindMeApp remindMeApp);

    void inject(MainActivity mainActivity);

    void inject(AddNewReminderActivity addNewReminderActivity);
}
