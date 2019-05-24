package com.avukelic.remindme;

import android.app.Application;

import com.avukelic.remindme.di.component.AppComponent;
import com.avukelic.remindme.di.component.DaggerAppComponent;
import com.avukelic.remindme.di.module.AppModule;

public class RemindMeApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .build();

        getAppComponent().inject(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
