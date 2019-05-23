package com.avukelic.remindme.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reminder implements Parcelable {

    private int id;
    private String task;
    private int deadLine;

    public Reminder(int id, String task, int deadLine) {
        this.id = id;
        this.task = task;
        this.deadLine = deadLine;
    }

    protected Reminder(Parcel in) {
        id = in.readInt();
        task = in.readString();
        deadLine = in.readInt();
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

    public void setId(int id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(task);
        dest.writeInt(deadLine);
    }
}
