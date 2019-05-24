package com.avukelic.remindme.view.reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.avukelic.remindme.R;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.model.Reminder;
import com.avukelic.remindme.util.DateUtil;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class AddNewReminderActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener,
        DialogInterface.OnCancelListener, DialogInterface.OnDismissListener, TimePickerDialog.OnTimeSetListener {

    public static final int ADD_NEW_REMINDER_CODE = 100;
    public static final String NEW_REMINDER_KEY = AddNewReminderActivity.class.getSimpleName() + ".new_reminder";

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
    List<MaterialCheckBox> priorityCheckBoxes;

    @OnClick({R.id.cb_priority_low, R.id.cb_priority_medium, R.id.cb_priority_high})
    void onPriorityChanged(View view) {
        for (MaterialCheckBox priorityCheckBox : priorityCheckBoxes) {
            priorityCheckBox.setChecked(priorityCheckBox.getId() == view.getId());
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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return super.onOptionsItemSelected(item);
        }
        boolean inputError = false;
        inputError = isInputValid(titleInput);
        inputError = isInputValid(taskInput);

        if (!inputError) {
            if (item.getItemId() == R.id.add_reminder) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(NEW_REMINDER_KEY, getReminder());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //region Time picker
    private void openTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        String[] time = remindMeOnTime.getText().toString().split(":");
        int hour = isEditTextEmpty(remindMeOnTime) ? mcurrentTime.get(Calendar.HOUR_OF_DAY) : Integer.parseInt(time[0]);
        int minute = isEditTextEmpty(remindMeOnTime) ? mcurrentTime.get(Calendar.MINUTE) : Integer.parseInt(time[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, this, hour, minute, true);
        timePickerDialog.show();
        setAlertDialogButtons(timePickerDialog);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        remindMeOnTime.setText(getString(R.string.time, hourOfDay, minute));
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
        setAlertDialogButtons(datePickerDialog);
    }

    private void setAlertDialogButtons(AlertDialog dialog) {
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        if (month < 9) {
            stringBuilder.append("0");
        }
        stringBuilder.append(month + 1);
        stringBuilder.append("-");
        if (dayOfMonth < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(dayOfMonth);
        if (!DateUtil.isPickedDayBeforeToday(stringBuilder.toString())) {
            remindMeOnDate.setText(stringBuilder.toString());
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

    private Reminder getReminder() {
        // TODO: 23.5.2019. fix id and deadline
        return new Reminder(UUID.randomUUID().toString(),
                taskInput.getText().toString().trim(),
                titleInput.getText().toString().trim(),
                5050505,
                Reminder.Priority.HIGH);
    }

    private boolean isEditTextEmpty(TextView text) {
        return text.getText().toString().trim().isEmpty();
    }

    private boolean isInputValid(TextView input) {
        if (input.getText().toString().trim().isEmpty()) {
            input.setError(getString(R.string.empty_input_field));
            return true;
        }
        input.setError(null);
        return false;
    }


}
