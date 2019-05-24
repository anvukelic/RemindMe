package com.avukelic.remindme.di.module;

import android.content.Context;

import com.avukelic.remindme.RemindMeApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private RemindMeApp remindMeApp;

    public AppModule(RemindMeApp remindMeApp){
        this.remindMeApp = remindMeApp;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return this.remindMeApp;
    }
}
