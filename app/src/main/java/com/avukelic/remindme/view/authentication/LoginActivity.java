package com.avukelic.remindme.view.authentication;

import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import com.avukelic.remindme.R;
import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.data.model.ReminderModel;
import com.avukelic.remindme.data.model.User;
import com.avukelic.remindme.database.UserDaoModel;
import com.avukelic.remindme.singleton.UserSingleton;
import com.avukelic.remindme.util.TextUtil;
import com.avukelic.remindme.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Inject
    public AuthViewModelFactory authViewModelFactory;

    private AuthViewModel authViewModel;


    //region ButterKnife
    @BindView(R.id.email)
    AppCompatEditText email;
    @BindView(R.id.password)
    AppCompatEditText password;

    @OnClick(R.id.login_button)
    void loginUser() {
        if (!TextUtil.isInputValid(this, email) & !TextUtil.isInputValid(this, password)) {
            RemindMeApp.getFirebaseAuth().signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = RemindMeApp.getFirebaseAuth().getCurrentUser();
                            UserDaoModel user = new UserDaoModel(firebaseUser.getUid(), firebaseUser.getEmail(), new ReminderModel());
                            UserSingleton userSingleton = UserSingleton.getInstance();
                            userSingleton.setUser(user);
                            authViewModel.saveUser(user);
                            MainActivity.launchActivity(LoginActivity.this);
                        } else {
                            shortToast(getString(R.string.login_not_successful));
                        }
                    });
        }
    }
    //endregion

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initUI() {
        authViewModel.getUser();
    }

    @Override
    protected void initViewModel() {
        RemindMeApp.getAppComponent().inject(this);
        authViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel.class);
        observeData();
    }

    private void observeData() {
    }

}
