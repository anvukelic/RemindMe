package com.avukelic.remindme.view.reminder;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.avukelic.remindme.R;
import com.avukelic.remindme.data.model.Reminder;
import com.avukelic.remindme.util.DateUtil;
import com.avukelic.remindme.util.GlideUtil;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<Reminder> reminders = new ArrayList<>();
    private OnReminderClickListener listener;

    public void setReminders(List<Reminder> newReminders) {
        reminders.clear();
        reminders.addAll(newReminders);
        Collections.reverse(reminders);
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

    public int removeReminder(int position){
        notifyItemRemoved(position);
        int id = reminders.get(position).getId();
        reminders.remove(position);
        return id;
    }


    class ReminderViewHolder extends RecyclerView.ViewHolder {

        //region ButterKnife
        @BindView(R.id.cv_root)
        MaterialCardView cardView;
        @BindView(R.id.taskTitle)
        AppCompatTextView task;
        @BindView(R.id.deadline)
        AppCompatTextView deadline;
        @BindView(R.id.priority_box)
        ImageView priorityBox;

        ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        //endregion

        @OnClick(R.id.cl_root_reminder_item)
        void onItemClick() {
            listener.onReminderClick(getAdapterPosition());
        }

        @OnLongClick(R.id.cl_root_reminder_item)
        boolean onItemLongClick(){
            listener.onReminderLongPress(getAdapterPosition());
            return true;
        }


        public void setData(Reminder reminder) {
            task.setText(reminder.getTaskTitle());
            deadline.setText(!reminder.isInPast() ? itemView.getContext().getString(R.string.deadline_error_message) : DateUtil.parseDateFromLongToString(reminder.getDeadLine()));
            switch (reminder.getPriority()) {
                case LOW:
                    GlideUtil.loadColor(itemView.getContext(), priorityBox, R.color.colorPriorityLow);
                    break;
                case MEDIUM:
                    GlideUtil.loadColor(itemView.getContext(), priorityBox, R.color.colorPriorityMedium);
                    break;
                case HIGH:
                    GlideUtil.loadColor(itemView.getContext(), priorityBox, R.color.colorPriorityHigh);
                    break;
            }
        }
    }

    public interface OnReminderClickListener {
        void onReminderClick(int position);
        void onReminderLongPress(int position);
    }
}