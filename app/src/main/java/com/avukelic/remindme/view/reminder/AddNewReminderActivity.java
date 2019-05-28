package com.avukelic.remindme.view.reminder;

import android.app.Activity;
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

public class AddNewReminderActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener,
        DialogInterface.OnCancelListener, DialogInterface.OnDismissListener, TimePickerDialog.OnTimeSetListener {

    public static final String NEW_REMINDER_ID_KEY = AddNewReminderActivity.class.getSimpleName() + ".new_reminder_id";
    public static final String NEW_REMINDER_TITLE_KEY = AddNewReminderActivity.class.getSimpleName() + ".new_reminder_title";

    @Inject
    public ReminderViewModelFactory reminderViewModelFactory;
    private ReminderViewModel viewModel;
    private Reminder.Priority priority = Reminder.Priority.LOW;


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

    public static void launchActivityForResult(Context context) {
        Intent intent = new Intent(context, AddNewReminderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_new_reminder;
    }

    @Override
    protected void initUI() {
        root.requestFocus();
        initToolbar(toolbar, true, this.getString(R.string.new_reminder_toolbar_title));
    }

    @Override
    protected void initViewModel() {
        RemindMeApp.getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(this, reminderViewModelFactory).get(ReminderViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_reminder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return super.onOptionsItemSelected(item);
        }

        if (item.getItemId() == R.id.add_reminder) {
            boolean inputError = TextUtil.isInputValid(this, taskInput)
                    | TextUtil.isInputValid(this, titleInput)
                    | TextUtil.isInputValid(this, remindMeOnDate)
                    | TextUtil.isInputValid(this, remindMeOnTime);
            if (!inputError) {
                try {
                    viewModel.addReminder(getReminder()).observeSingle(this, reminder -> {
                        shortToast(getString(R.string.new_reminder_added));
                        finish();
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.

                onOptionsItemSelected(item);

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
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.setOnDismissListener(this);
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

    @Override
    public void onCancel(DialogInterface dialog) {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
    //endregion


    //Create reminder object and set alarm for notification
    private Reminder getReminder() throws ParseException {
        long time = DateUtil.parseDateFromString(getDateTime());
        Reminder reminder = new Reminder(taskInput.getText().toString().trim(),
                titleInput.getText().toString().trim(),
                time,
                priority);
        if (notificationSwitch.isChecked()) {
            Bundle bundle = new Bundle();
            bundle.putInt(NEW_REMINDER_ID_KEY, reminder.getId());
            bundle.putString(NEW_REMINDER_TITLE_KEY, titleInput.getText().toString().trim());
            new ReminderAlarm(this, bundle, time /*- 120000*/);
        }
        return reminder;

    }

    private String getDateTime() {
        return remindMeOnDate.getText().toString().trim() + " " + remindMeOnTime.getText().toString().trim();
    }


}
