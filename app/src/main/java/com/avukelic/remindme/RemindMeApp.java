package com.avukelic.remindme;

import android.app.Application;

import com.avukelic.remindme.di.component.AppComponent;
import com.avukelic.remindme.di.component.DaggerAppComponent;
import com.avukelic.remindme.di.module.AppModule;
import com.google.firebase.auth.FirebaseAuth;

public class RemindMeApp extends Application {

    private static AppComponent appComponent;
    private static FirebaseAuth firebaseAuth;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        getAppComponent().inject(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
