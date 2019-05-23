package com.avukelic.remindme.view;

import android.content.Intent;
import android.view.MenuItem;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avukelic.remindme.R;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.model.Reminder;
import com.avukelic.remindme.view.reminders.AddNewReminderActivity;
import com.avukelic.remindme.view.reminders.ReminderAdapter;
import com.avukelic.remindme.widgets.DrawerBottomSheet;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements DrawerBottomSheet.DrawerBottomSheetActionCallback, ReminderAdapter.OnReminderClickListener {

    private ReminderAdapter reminderAdapter;

    //region ButterKnife
    @BindView(R.id.bottom_app_bar)
    BottomAppBar bottomAppBar;
    @BindView(R.id.rv_reminders)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    @OnClick(R.id.fab_add_reminder)
    void onFabClicked() {
        AddNewReminderActivity.launchActivityForResult(this);
    }

    //endregion


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DrawerBottomSheet drawerBottomSheet = new DrawerBottomSheet();
                drawerBottomSheet.show(getSupportFragmentManager(), drawerBottomSheet.getTag());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(bottomAppBar);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        reminderAdapter = new ReminderAdapter();
        reminderAdapter.setListener(this);
        recyclerView.setAdapter(reminderAdapter);
        List<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder(0, "test", 535353));
        reminders.add(new Reminder(1, "test2", 5353535));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminders.add(new Reminder(2, "test3", 5353536));
        reminderAdapter.setReminders(reminders);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AddNewReminderActivity.ADD_NEW_REMINDER_CODE) {
            if (resultCode == RESULT_OK) {
                reminderAdapter.addNewReminder(data.getParcelableExtra(AddNewReminderActivity.NEW_REMINDER_KEY));
            }
        }
    }

    @Override
    public void onReminderClick(int position) {

    }
}
