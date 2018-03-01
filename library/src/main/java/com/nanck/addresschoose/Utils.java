package com.nanck.addresschoose;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * XXX 不好的方式
 * Activity 管理工具类
 *
 * @author nanck 2017/12/12.
 */

final class Utils {
    private static final String TAG = "Utils";
    private static Utils mInstance;
    private List<Activity> mActivitys = new ArrayList<>();

    private Utils() {
    }

    static Utils getInstance() {
        if (mInstance == null) {
            mInstance = new Utils();
        }
        return mInstance;
    }

    void put(Activity activity) {
        if (mActivitys != null) {
            try {
                mActivitys.add(activity);
            } catch (NullPointerException e) {
                Log.d(TAG, "ACTIVITY NULL");
            }
        }
    }

    void clearAll() {
        for (Activity a : mActivitys) {
            if (!a.isFinishing())
                a.finish();
        }
        mActivitys.clear();
    }

}