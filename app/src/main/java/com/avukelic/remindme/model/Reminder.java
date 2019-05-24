package com.avukelic.remindme.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder")
public class Reminder implements Parcelable{

    @PrimaryKey
    @ColumnInfo(name = "reminderId")
    private String id;
    @ColumnInfo(name = "task")
    private String task;
    @ColumnInfo(name = "taskTitle")
    private String taskTitle;
    @ColumnInfo(name = "deadline")
    private int deadLine;
    @ColumnInfo(name = "priority")
    private Priority priority;

    public Reminder(String id, String task, String taskTitle, int deadLine, Priority priority) {
        this.id = id;
        this.task = task;
        this.taskTitle = taskTitle;
        this.deadLine = deadLine;
        this.priority = priority;
    }

    protected Reminder(Parcel in) {
        id = in.readString();
        task = in.readString();
        taskTitle = in.readString();
        deadLine = in.readInt();
        priority = Priority.valueOf(in.readString());
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
        dest.writeString(priority.name());
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
