
package com.custom;

import android.util.Log;

public class LogM {

    private static String LOG_TAG = "log_tag";

    public static void d(String log) {
        Log.d(LOG_TAG, log);
    }

    public static void e(String log) {
        Log.e(LOG_TAG, log);
    }

    public static void w(String log) {
        Log.w(LOG_TAG, log);
    }

    public static void i(String log) {
        // TODO Auto-generated method stub
        Log.i(LOG_TAG, log);
    }
}
