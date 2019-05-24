package com.avukelic.remindme.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reminder implements Parcelable {

    private String id;
    private String task;
    private String taskTitle;
    private int deadLine;
    private Priority priority;

    public Reminder(String id, String task, String taskTitle, int deadLine, Priority priority) {
        this.id = id;
        this.task = task;
        this.taskTitle = taskTitle;
        this.deadLine = deadLine;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(int deadLine) {
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

    protected Reminder(Parcel in) {
        id = in.readString();
        task = in.readString();
        taskTitle = in.readString();
        deadLine = in.readInt();
        priority = Priority.values()[in.readInt()-1];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(task);
        dest.writeString(taskTitle);
        dest.writeInt(deadLine);
        dest.writeInt(priority.getType());
    }

    public enum Priority {
        LOW(1),
        MEDIUM(2),
        HIGH(3);

        private int type;

        private Priority(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
