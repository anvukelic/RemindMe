package com.avukelic.remindme.view.reminders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avukelic.remindme.R;
import com.avukelic.remindme.model.Reminder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<Reminder> reminders = new ArrayList<>();
    private OnReminderClickListener listener;

    public void setReminders(List<Reminder> newReminders) {
        reminders.clear();
        reminders.addAll(newReminders);
        notifyDataSetChanged();
    }

    public void addNewReminder(Reminder reminder) {
        reminders.add(reminder);
        notifyItemInserted(reminders.size());
    }

    public void setListener(OnReminderClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        holder.setData(reminders.get(position));
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public Reminder getReminder(int position) {
        return reminders.get(position);
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {

        //region ButterKnife
        @BindView(R.id.task)
        AppCompatTextView task;
        @BindView(R.id.deadline)
        AppCompatTextView deadline;
        //endregion

        ReminderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(Reminder reminder){
            task.setText(reminder.getTask());
            deadline.setText(String.valueOf(reminder.getDeadLine()));
        }
    }

    public interface OnReminderClickListener {
        void onReminderClick(int position);
    }
}