package com.avukelic.remindme.data.repository;

import com.avukelic.remindme.data.model.User;

public interface AuthRepository {

    void saveUser(User user);
}
