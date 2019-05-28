package com.avukelic.remindme.view.reminder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.avukelic.remindme.base.BaseViewModel;
import com.avukelic.remindme.data.model.Reminder;
import com.avukelic.remindme.data.repository.ReminderRepository;
import com.avukelic.remindme.data.repository.SingleLiveEvent;

import java.util.List;

public class ReminderViewModel extends BaseViewModel {

    private ReminderRepository reminderRepository;
    private MediatorLiveData<List<Reminder>> mediatorLiveData = new MediatorLiveData<>();

    public ReminderViewModel(ReminderRepository reminderRepository){
        this.reminderRepository = reminderRepository;
    }

    public SingleLiveEvent<Reminder> addReminder(Reminder reminder){
        return reminderRepository.addReminder(reminder);
    }

    public void getReminders(){
        mediatorLiveData.addSource(reminderRepository.getReminders(), mediatorLiveData::setValue);
    }

    public MediatorLiveData<List<Reminder>> getMediatorLiveData() {
        return mediatorLiveData;
    }


}
