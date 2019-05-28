package com.avukelic.remindme.view;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avukelic.remindme.R;
import com.avukelic.remindme.RemindMeApp;
import com.avukelic.remindme.base.BaseActivity;
import com.avukelic.remindme.singleton.UserSingleton;
import com.avukelic.remindme.view.reminder.AddNewReminderActivity;
import com.avukelic.remindme.view.reminder.ReminderAdapter;
import com.avukelic.remindme.view.reminder.ReminderViewModel;
import com.avukelic.remindme.view.reminder.ReminderViewModelFactory;
import com.avukelic.remindme.widgets.DrawerBottomSheet;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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
        viewModel.getMediatorLiveData().observe(this, reminders -> reminderAdapter.setReminders(reminders));
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
        /*List<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder( "test", "test", 535353, Reminder.Priority.LOW));
        reminders.add(new Reminder( "test2", "test", 5353535, Reminder.Priority.LOW));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.MEDIUM));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.LOW));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.HIGH));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.LOW));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.LOW));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.LOW));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.MEDIUM));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.LOW));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.MEDIUM));
        reminders.add(new Reminder("test3", "test", 5353536, Reminder.Priority.HIGH));
        reminderAdapter.setReminders(reminders);*/
    }

    @Override
    public void onReminderClick(int position) {

    }
}
