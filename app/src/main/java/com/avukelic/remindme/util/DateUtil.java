package com.avukelic.remindme.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static final String DATE_FORMAT = "dd.MM.yyyy.";
    public static final String TIME_FORMAT = "HH:mm";

    public static boolean isPickedDayBeforeToday(String date) {
        long now = new Date().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            if (dateFormat.parse(dateFormat.format(now)).getTime() > dateFormat.parse(date).getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long parseDateFromString(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
        return dateFormat.parse(date).getTime();
    }

    public static String parseDateFromLongToString(long time) {
        Date date = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
        return df2.format(date);
    }

    public static String[] getDateTime(long dateTime){
        Date date = new Date(dateTime);
        String[] time = new String[2];
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        time[0] = dateFormat.format(date);
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        time[1] = dateFormat.format(date);
        return time;
    }
}
