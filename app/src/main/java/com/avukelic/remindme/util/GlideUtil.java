package com.avukelic.remindme.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtil {

    public static void loadColor(Context context, ImageView imageView, int color){
        Glide.with(context)
                .load(new ColorDrawable(ContextCompat.getColor(context, color)))
                .apply(new RequestOptions().circleCrop())
                .into(imageView);
    }
}
