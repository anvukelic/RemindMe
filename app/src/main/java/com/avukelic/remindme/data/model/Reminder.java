package com.avukelic.remindme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.avukelic.remindme.singleton.UserSingleton;

public class Reminder implements Parcelable {


    private int id;

    private String task;
    private String taskTitle;
    private long deadLine;
    private Priority priority;
    private boolean notificationEnabled;

    public Reminder(String task, String taskTitle, long deadLine, Priority priority, boolean notificationEnabled) {
        id = UserSingleton.getInstance().getLastReminderId();
        this.task = task;
        this.taskTitle = taskTitle;
        this.deadLine = deadLine;
        this.priority = priority;
        this.notificationEnabled = notificationEnabled;
    }


    protected Reminder(Parcel in) {
        id = in.readInt();
        task = in.readString();
        taskTitle = in.readString();
        deadLine = in.readLong();
        priority = Priority.valueOf(in.readString());
        notificationEnabled = in.readByte() != 0;
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(long deadLine) {
        this.deadLine = deadLine;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public boolean isNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public Reminder() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(task);
        dest.writeString(taskTitle);
        dest.writeLong(deadLine);
        dest.writeString(priority.name());
        dest.writeByte((byte) (notificationEnabled ? 1 : 0));
    }


    public enum Priority {
        LOW(1),
        MEDIUM(2),
        HIGH(3);

        private int type;

        Priority(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
