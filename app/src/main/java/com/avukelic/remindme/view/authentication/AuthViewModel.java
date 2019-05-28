package com.avukelic.remindme.view.authentication;

import androidx.lifecycle.MutableLiveData;

import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.base.BaseViewModel;
import com.avukelic.remindme.data.model.ReminderModel;
import com.avukelic.remindme.data.model.User;
import com.avukelic.remindme.data.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

class AuthViewModel extends BaseViewModel {

    private AuthRepository repository;

    AuthViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    void saveUser() {
        FirebaseUser firebaseUser = RemindMeApp.getFirebaseAuth().getCurrentUser();
        User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());
        repository.saveUser(user);
    }

}
