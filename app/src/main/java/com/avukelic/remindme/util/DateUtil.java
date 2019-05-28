package com.avukelic.remindme.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static boolean isPickedDayBeforeToday(String date) {
        long now = new Date().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return dateFormat.parse(date).getTime();
    }

    public static String parseDateFromLongToString(long time) {
        Date date = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return df2.format(date);
    }
}
