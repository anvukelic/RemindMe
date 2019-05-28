package com.avukelic.remindme.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.avukelic.remindme.R;
import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.singleton.UserSingleton;
import com.avukelic.remindme.view.authentication.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_SLEEP = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.SplashScreenTheme);
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        startSplashSleep();
    }

    private void startSplashSleep() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            FirebaseUser currentUser = RemindMeApp.getFirebaseAuth().getCurrentUser();
            if (currentUser != null) {
                UserSingleton.getInstance().setInitUser(currentUser);
                MainActivity.launchActivity(SplashScreen.this);
            } else {
                LoginActivity.launchActivity(SplashScreen.this);
            }
        }, SPLASH_SLEEP);
    }
}
