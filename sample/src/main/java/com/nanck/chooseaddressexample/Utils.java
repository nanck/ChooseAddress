package com.nanck.chooseaddressexample;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

public final class Utils {

    public static boolean isEmptyForViews(@NonNull TextView[] view) {
        for (TextView textView : view) {
            if (TextUtils.isEmpty(getText(textView)))
                return true;
        }
        return false;
    }

    public static String getText(@NonNull TextView view) {
        return view.getText().toString().trim();
    }

}