package com.avukelic.remindme.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.avukelic.remindme.data.model.User;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM userData")
    User getUser();

    @Insert(onConflict = REPLACE)
    void saveUser(UserDaoModel userDaoModel);

    @Query("DELETE FROM userData where id =:userId")
    void deleteUser(final String userId);
}
