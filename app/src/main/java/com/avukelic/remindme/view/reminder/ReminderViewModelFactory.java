package com.avukelic.remindme.view.reminder;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.avukelic.remindme.data.repository.AuthRepositoryImpl;
import com.avukelic.remindme.data.repository.ReminderRepository;
import com.avukelic.remindme.data.repository.ReminderRepositoryImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReminderViewModelFactory implements ViewModelProvider.Factory {

    private final ReminderRepositoryImpl reminderRepository;

    @Inject
    public ReminderViewModelFactory(ReminderRepositoryImpl reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReminderViewModel.class)) {
            return (T) new ReminderViewModel(reminderRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
