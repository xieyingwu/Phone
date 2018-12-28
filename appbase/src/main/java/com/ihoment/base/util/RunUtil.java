package com.ihoment.base.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by wuwenlong on 13/06/2017.
 */

public class RunUtil {
    public static void runOnUiThread(Context context,Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(context.getMainLooper()).post(runnable);
            return;
        }
        runnable.run();
    }
}
