package com.avukelic.remindme.view.authentication;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.avukelic.remindme.data.repository.AuthRepositoryImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthViewModelFactory implements ViewModelProvider.Factory {

    private final AuthRepositoryImpl authRepository;

    @Inject
    public AuthViewModelFactory(AuthRepositoryImpl authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(authRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
