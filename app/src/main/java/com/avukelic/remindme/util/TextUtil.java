package com.avukelic.remindme.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.avukelic.remindme.R;

public class TextUtil {

    //region Text checkers
    public static boolean isInputValid(Context context, TextView input) {
        if (input.getText().toString().trim().isEmpty()) {
            input.setError(context.getString(R.string.empty_input_field));
            return true;
        }
        input.setError(null);
        return false;
    }

    public static boolean isEditTextEmpty(TextView text) {
        return text.getText().toString().trim().isEmpty();
    }
    //endregion

    //region Alert dialog helper
    public static String formatTime(int hours, int minutes){
        StringBuilder stringBuilder = new StringBuilder();
        if (hours < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(hours);
        stringBuilder.append(":");
        if (minutes < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(minutes);
        return stringBuilder.toString();
    }

    public static String formatDate(int year, int month, int day){
        StringBuilder stringBuilder = new StringBuilder();
        if (day < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(day);
        stringBuilder.append(".");
        if (month < 9) {
            stringBuilder.append("0");
        }
        stringBuilder.append(month + 1);
        stringBuilder.append(".");
        stringBuilder.append(year);
        stringBuilder.append(".");
        return stringBuilder.toString();
    }

    public static void setAlertDialogButtons(Context context, AlertDialog dialog) {
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }
    //endregion
}
