package ru.vladus177.customview.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingUtils {

    @BindingAdapter("textfromint")
    public static void setTextfromint(TextView tv, int value) {

        tv.setText(String.valueOf(value));

    }

    @BindingAdapter(value = {"imageCompleted", "icCompleted", "icActive"})
    public static void setCompleted(final ImageView imageView, final boolean completed, final Drawable icCompleted, final Drawable icActive) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (completed) {
                    imageView.setImageDrawable(icCompleted);
                } else {
                    imageView.setImageDrawable(icActive);
                }
            }
        }, 400);

    }
}
