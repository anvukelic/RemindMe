package com.avukelic.remindme.view.authentication;

import androidx.lifecycle.MutableLiveData;

import com.avukelic.remindme.base.BaseViewModel;
import com.avukelic.remindme.data.model.User;
import com.avukelic.remindme.data.repository.AuthRepository;
import com.avukelic.remindme.database.UserDaoModel;

class AuthViewModel extends BaseViewModel {

    private AuthRepository repository;

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    AuthViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    void saveUser(UserDaoModel user){
        repository.saveUser(user);
    }

    void getUser(){
        userLiveData.setValue(repository.getUser());
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
