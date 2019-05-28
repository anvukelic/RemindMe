package com.avukelic.remindme.view.authentication;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import com.avukelic.remindme.R;
import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.util.TextUtil;
import com.avukelic.remindme.view.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Inject
    public AuthViewModelFactory authViewModelFactory;
    private AuthViewModel authViewModel;

    //region ButterKnife
    @BindView(R.id.email)
    AppCompatEditText email;
    @BindView(R.id.password)
    AppCompatEditText password;

    @OnClick(R.id.login_button)
    void createUser() {
        if (!TextUtil.isInputValid(this, email) & !TextUtil.isInputValid(this, password)) {
            RemindMeApp.getFirebaseAuth().createUserWithEmailAndPassword(email.getText().toString().trim(),
                    password.getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            authViewModel.saveUser();
                            MainActivity.launchActivityAndRemoveHistory(RegisterActivity.this);
                        } else {
                            shortToast(getString(R.string.login_not_successful));
                        }
                    });
        }
    }
    //endregion

    public static void launchActivity(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initUI() {

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
