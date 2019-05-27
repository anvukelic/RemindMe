package com.avukelic.remindme.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.avukelic.remindme.R;
import com.avukelic.remindme.view.reminder.AddNewReminderActivity;
import com.avukelic.remindme.view.reminder.SingleReminderActivity;

public class ReminderAlarm extends BroadcastReceiver {
    private final String REMINDER_BUNDLE = "MyReminderBundle";

    // this constructor is called by the alarm manager.
    public ReminderAlarm() {
    }

    // you can use this constructor to create the alarm. 
    //  Just pass in the main activity as the context, 
    //  any extras you'd like to get later when triggered 
    //  and the timeout
    public ReminderAlarm(Context context, Bundle extras, long timeToTrigger) {
        AlarmManager alarmMgr =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderAlarm.class);
        intent.putExtra(REMINDER_BUNDLE, extras);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, (int)timeToTrigger, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, timeToTrigger,
                pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // here you can get the extras you passed in when creating the alarm
        Bundle bundle = intent.getBundleExtra(REMINDER_BUNDLE);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "10001")
                .setSmallIcon(R.drawable.ic_action_add)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(context.getString(R.string.push_title))
                .setContentText(bundle.getString(AddNewReminderActivity.NEW_REMINDER_TITLE_KEY))
                .setContentIntent(getPendingIntent(context, bundle.getInt(AddNewReminderActivity.NEW_REMINDER_ID_KEY)))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("10001", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLightColor(ContextCompat.getColor(context, R.color.colorAccent));
            notificationChannel.enableVibration(true);
            builder.setChannelId("10001");
            manager.createNotificationChannel(notificationChannel);
        }

        pushNotification(manager, builder.build());
    }

    private PendingIntent getPendingIntent(Context context, int reminderId) {
        Intent intent = new Intent(context, SingleReminderActivity.class);
        intent.putExtra(AddNewReminderActivity.NEW_REMINDER_ID_KEY, reminderId);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    protected void pushNotification(NotificationManager manager, Notification notification) {
        manager.notify(101, notification);
    }
}