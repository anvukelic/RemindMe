package com.avukelic.remindme.view.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.avukelic.remindme.R;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.data.model.Reminder;
import com.avukelic.remindme.util.DateUtil;
import com.avukelic.remindme.util.TextUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class SingleReminderActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener,
        DialogInterface.OnCancelListener, DialogInterface.OnDismissListener, TimePickerDialog.OnTimeSetListener {

    private Reminder.Priority priority;

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

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            titleInput.setText("Test");
        }
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
}
