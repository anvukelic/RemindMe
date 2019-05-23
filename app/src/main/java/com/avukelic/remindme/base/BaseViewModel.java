package com.avukelic.remindme.base;


import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    protected BaseViewModel(){
        compositeDisposable = new CompositeDisposable();
    }

    protected void addSubscription(Disposable subscription){
        compositeDisposable.add(subscription);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}