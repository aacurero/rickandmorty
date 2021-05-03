package com.example.simpledata.rmapp.util;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.simpledata.rmapp.R;

public class Utils {
    public static MaterialDialog createProgressDialog(Context context, String content) {
        return new MaterialDialog.Builder(context)
                .content(content)
                .contentColor(ContextCompat.getColor(context, android.R.color.black))
                .progress(true, 0)
                .cancelable(false)
                .backgroundColor(ContextCompat.getColor(context, android.R.color.white))
                .widgetColor(ContextCompat.getColor(context, R.color.blueDark))
                .build();
    }
}
