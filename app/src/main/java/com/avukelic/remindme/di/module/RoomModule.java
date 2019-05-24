package com.avukelic.remindme.di.module;

import android.content.Context;

import androidx.room.Room;

import com.avukelic.remindme.database.DataBase;
import com.avukelic.remindme.database.UserDao;
import com.avukelic.remindme.database.UserDaoModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class})
public abstract class RoomModule {

    @Singleton
    @Provides
    static DataBase providesRoomDataBase(Context remindMeApp) {
        return Room.databaseBuilder(remindMeApp, DataBase.class, "remind3MeDb.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static UserDao providesUserDataDao(DataBase database) {
        return database.userModelDao();
    }

}
