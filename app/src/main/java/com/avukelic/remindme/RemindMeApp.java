package com.avukelic.remindme;

import android.app.Application;
import android.content.Context;

import com.avukelic.remindme.di.component.AppComponent;
import com.avukelic.remindme.di.component.DaggerAppComponent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemindMeApp extends Application {

    private static AppComponent appComponent;
    private static FirebaseAuth firebaseAuth;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        context = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .build();

        getAppComponent().inject(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static Context getContext() {
        return context;
    }
}
