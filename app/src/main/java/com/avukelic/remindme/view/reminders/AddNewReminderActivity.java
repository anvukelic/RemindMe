package com.avukelic.remindme.view.reminders;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avukelic.remindme.R;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.model.Reminder;

import butterknife.BindView;

public class AddNewReminderActivity extends BaseActivity {

    public static final int ADD_NEW_REMINDER_CODE = 100;
    public static final String NEW_REMINDER_KEY = AddNewReminderActivity.class.getSimpleName() + ".new_reminder";

    //region ButterKnife
    @BindView(R.id.toolbar_new_reminder)
    Toolbar toolbar;
    @BindView(R.id.root_add_new_reminder)
    LinearLayout root;
    @BindView(R.id.input_add_new_reminder)
    EditText taskInput;

    //endregion

    public static void launchActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, AddNewReminderActivity.class);
        activity.startActivityForResult(intent, ADD_NEW_REMINDER_CODE);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_new_reminder;
    }

    @Override
    protected void initUI() {
        root.requestFocus();
        initToolbar(toolbar, true, "New reminder");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_reminder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_reminder) {
            Intent returnIntent = new Intent();
            // TODO: 23.5.2019. fix id and deadline 
            returnIntent.putExtra(NEW_REMINDER_KEY, new Reminder(4, taskInput.getText().toString().trim(), 5050505));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
