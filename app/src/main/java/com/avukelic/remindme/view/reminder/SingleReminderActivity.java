package com.avukelic.remindme.view.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.avukelic.remindme.R;
import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.alarm.ReminderAlarm;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.data.model.Reminder;
import com.avukelic.remindme.util.DateUtil;
import com.avukelic.remindme.util.TextUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

import static com.avukelic.remindme.view.reminder.AddNewReminderActivity.NEW_REMINDER_ID_KEY;
import static com.avukelic.remindme.view.reminder.AddNewReminderActivity.NEW_REMINDER_TITLE_KEY;

public class SingleReminderActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String SINGLE_REMINDER_KEY = SingleReminderActivity.class.getSimpleName() + ".bundle_single_reminder";

    @Inject
    public ReminderViewModelFactory factory;
    private ReminderViewModel viewModel;
    private Reminder.Priority priority;
    private Reminder initialReminder;

    //region ButterKnife
    @BindView(R.id.toolbar_new_reminder)
    Toolbar toolbar;
    @BindView(R.id.root_add_new_reminder)
    LinearLayout root;
    @BindView(R.id.input_task_add_new_reminder)
    EditText taskInput;
    @BindView(R.id.input_title_add_new)
    EditText titleInput;
    @BindView(R.id.tv_remind_me_on_date)
    TextView remindMeOnDate;
    @BindView(R.id.tv_remind_me_on_time)
    TextView remindMeOnTime;
    @BindView(R.id.switch_push_notification_reminder)
    SwitchCompat notificationSwitch;
    @BindViews({R.id.cb_priority_low, R.id.cb_priority_medium, R.id.cb_priority_high})
    List<AppCompatRadioButton> priorityCheckBoxes;

    @OnClick({R.id.cb_priority_low, R.id.cb_priority_medium, R.id.cb_priority_high})
    void onPriorityChanged(View view) {
        for (AppCompatRadioButton priorityCheckBox : priorityCheckBoxes) {
            priorityCheckBox.setChecked(view.getId() == priorityCheckBox.getId());
            priority = Reminder.Priority.values()[priorityCheckBoxes.indexOf(priorityCheckBox)];
        }

    }

    @OnClick({R.id.tv_remind_me_on_date, R.id.tv_remind_me_on_time})
    void onDateClick(View view) {
        if (view.getId() == R.id.tv_remind_me_on_date) {
            openDatePicker();
        } else {
            openTimePicker();
        }
    }

    //endregion

    public static void launchActivity(Context context, Reminder reminder) {
        Intent intent = new Intent(context, SingleReminderActivity.class);
        intent.putExtra(SINGLE_REMINDER_KEY, reminder);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_new_reminder;
    }

    @Override
    protected void initUI() {
        root.requestFocus();
        initToolbar(toolbar, true, this.getString(R.string.single_reminder_toolbar_title));
        initData();
    }

    @Override
    protected void initViewModel() {
        RemindMeApp.getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(this, factory).get(ReminderViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_reminder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return super.onOptionsItemSelected(item);
        }

        if (item.getItemId() == R.id.update_reminder) {
            boolean inputError = TextUtil.isInputValid(this, taskInput)
                    | TextUtil.isInputValid(this, titleInput)
                    | TextUtil.isInputValid(this, remindMeOnDate)
                    | TextUtil.isInputValid(this, remindMeOnTime);
            if (!inputError) {
                try {
                    viewModel.addReminder(getReminder()).observeSingle(this, reminder -> {
                        shortToast(getString(R.string.reminder_is_updated));
                        finish();
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onOptionsItemSelected(item);

    }


    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            initialReminder = getIntent().getParcelableExtra(SINGLE_REMINDER_KEY);
        } else {
            return;
        }
        initViewWithData();
    }

    private void initViewWithData() {
        taskInput.setText(initialReminder.getTask());
        titleInput.setText(initialReminder.getTaskTitle());
        String[] dateTime = DateUtil.getDateTime(initialReminder.getDeadLine());
        remindMeOnDate.setText(dateTime[1]);
        remindMeOnTime.setText(dateTime[0]);
        notificationSwitch.setChecked(initialReminder.isNotificationEnabled());
        priorityCheckBoxes.get(initialReminder.getPriority().getType() - 1).performClick();
    }

    //region Time picker
    private void openTimePicker() {
        String[] time = remindMeOnTime.getText().toString().split(":");
        int hour = TextUtil.isEditTextEmpty(remindMeOnTime) ? 8 : Integer.parseInt(time[0]);
        int minute = TextUtil.isEditTextEmpty(remindMeOnTime) ? 0 : Integer.parseInt(time[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, this, hour, minute, true);
        timePickerDialog.show();
        TextUtil.setAlertDialogButtons(this, timePickerDialog);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        remindMeOnTime.setText(TextUtil.formatTime(hourOfDay, minute));
        remindMeOnTime.setError(null);
    }
    //endregion

    //region Date picker
    private void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        String[] date = remindMeOnDate.getText().toString().split("-");
        int year = remindMeOnDate.getText().toString().isEmpty() ? c.get(Calendar.YEAR) : Integer.parseInt(date[0]);
        int month = remindMeOnDate.getText().toString().isEmpty() ? c.get(Calendar.MONTH) : Integer.parseInt(date[1]) - 1;
        int days = remindMeOnDate.getText().toString().isEmpty() ? c.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(date[2]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, this, year, month, days);
        datePickerDialog.show();
        TextUtil.setAlertDialogButtons(this, datePickerDialog);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = TextUtil.formatDate(year, month, dayOfMonth);
        if (!DateUtil.isPickedDayBeforeToday(date)) {
            remindMeOnDate.setText(date);
            remindMeOnDate.setError(null);
        } else {
            Toast.makeText(this, R.string.date_pick_error_message_before, Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    private String getDateTime() {
        return remindMeOnDate.getText().toString().trim() + " " + remindMeOnTime.getText().toString().trim();
    }

    private Reminder getReminder() throws ParseException {
        long time = DateUtil.parseDateFromString(getDateTime());
        initialReminder.setTaskTitle(titleInput.getText().toString().trim());
        initialReminder.setTask(taskInput.getText().toString().trim());
        initialReminder.setDeadLine(time);
        initialReminder.setNotificationEnabled(notificationSwitch.isChecked());
        initialReminder.setPriority(priority);
        if (notificationSwitch.isChecked()) {
            Bundle bundle = new Bundle();
            bundle.putInt(NEW_REMINDER_ID_KEY, initialReminder.getId());
            bundle.putString(NEW_REMINDER_TITLE_KEY, titleInput.getText().toString().trim());
            new ReminderAlarm(this, bundle, time /*- 120000*/, initialReminder.isNotificationEnabled() ? ReminderAlarm.SET : ReminderAlarm.CANCEL);
        }
        return initialReminder;

    }
}
