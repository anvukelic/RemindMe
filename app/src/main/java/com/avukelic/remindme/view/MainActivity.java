package com.avukelic.remindme.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avukelic.remindme.R;
import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.alarm.ReminderAlarm;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.data.model.Reminder;
import com.avukelic.remindme.singleton.FirebaseDatabaseSingleton;
import com.avukelic.remindme.view.reminder.AddNewReminderActivity;
import com.avukelic.remindme.view.reminder.ReminderAdapter;
import com.avukelic.remindme.view.reminder.ReminderViewModel;
import com.avukelic.remindme.view.reminder.ReminderViewModelFactory;
import com.avukelic.remindme.view.reminder.SingleReminderActivity;
import com.avukelic.remindme.widgets.DrawerBottomSheet;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.avukelic.remindme.view.reminder.AddNewReminderActivity.NEW_REMINDER_ID_KEY;
import static com.avukelic.remindme.view.reminder.AddNewReminderActivity.NEW_REMINDER_PRIORITY_KEY;
import static com.avukelic.remindme.view.reminder.AddNewReminderActivity.NEW_REMINDER_TITLE_KEY;

public class MainActivity extends BaseActivity implements DrawerBottomSheet.DrawerBottomSheetActionCallback, ReminderAdapter.OnReminderClickListener {

    @Inject
    public ReminderViewModelFactory reminderViewModelFactory;

    private ReminderAdapter reminderAdapter;
    private ReminderViewModel viewModel;

    //region ButterKnife
    @BindView(R.id.fab_add_reminder)
    FloatingActionButton fabAdd;
    @BindView(R.id.bottom_app_bar)
    BottomAppBar bottomAppBar;
    @BindView(R.id.rv_reminders)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.empty_screen)
    LinearLayout emptyScreen;

    @OnClick(R.id.fab_add_reminder)
    void onFabClicked() {
        AddNewReminderActivity.launchActivityForResult(this);
    }

    //endregion


    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void launchActivityAndRemoveHistory(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        initToolbar();
        initRecyclerView();
    }

    @Override
    protected void initViewModel() {
        RemindMeApp.getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(this, reminderViewModelFactory).get(ReminderViewModel.class);
        observeData();
        viewModel.getReminders();
    }

    private void observeData() {
        viewModel.getMediatorLiveData().observe(this, reminders -> {
            if (reminders.size() > 0) {
                reminderAdapter.setReminders(reminders);
                setAlarmForReminders(reminders);
                hideEmptyScreen();
            } else {
                showEmptyScreen();
            }
        });
    }

    private void setAlarmForReminders(List<Reminder> reminders) {
        for (Reminder reminder : reminders) {
            if (reminder.isNotificationEnabled() && reminder.isInPast()) {
                Bundle bundle = new Bundle();
                bundle.putInt(NEW_REMINDER_ID_KEY, reminder.getId());
                bundle.putString(NEW_REMINDER_TITLE_KEY, reminder.getTaskTitle());
                bundle.putInt(NEW_REMINDER_PRIORITY_KEY, reminder.getPriority().getType());
                new ReminderAlarm(this, bundle, reminder.getDeadLine() /*- 120000*/, ReminderAlarm.SET);
            }
        }
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(bottomAppBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DrawerBottomSheet drawerBottomSheet = new DrawerBottomSheet();
                drawerBottomSheet.show(getSupportFragmentManager(), drawerBottomSheet.getTag());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        reminderAdapter = new ReminderAdapter();
        reminderAdapter.setListener(this);
        recyclerView.setAdapter(reminderAdapter);
    }

    @Override
    public void onReminderClick(int position) {
        Reminder reminder = reminderAdapter.getReminder(position);
        SingleReminderActivity.launchActivity(this, reminder);
    }

    @Override
    public void onReminderLongPress(int position) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(R.string.dialog_confirm, (dialog, which) -> {
                    int reminderId = reminderAdapter.removeReminder(position);
                    FirebaseDatabaseSingleton
                            .getInstance()
                            .getReminderDatabaseReference()
                            .child(String.valueOf(reminderId))
                            .removeValue();

                })
                .setNegativeButton(R.string.dialog_decline, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void showEmptyScreen() {
        emptyScreen.setVisibility(View.VISIBLE);
    }

    private void hideEmptyScreen() {
        emptyScreen.setVisibility(View.GONE);
    }
}
