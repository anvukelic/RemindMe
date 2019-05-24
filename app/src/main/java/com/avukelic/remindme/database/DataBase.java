package com.avukelic.remindme.database;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(entities = { UserDaoModel.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class DataBase extends RoomDatabase {

    public abstract UserDao userModelDao();

}