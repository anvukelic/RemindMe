package com.avukelic.remindme.data.repository;

import com.avukelic.remindme.data.model.User;
import com.avukelic.remindme.database.UserDaoModel;

public interface AuthRepository {

    void saveUser(UserDaoModel user);

    User getUser();

}
