package com.ihoment.base.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by wuwenlong on 3/23/16.
 */
public class WeakBroadcast extends BroadcastReceiver {
    private WeakReference<BaseActivity> activityWeakReference;

    public WeakBroadcast(BaseActivity activity) {
        activityWeakReference = new WeakReference(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final BaseActivity activity = activityWeakReference.get();
        if (activity != null) {
            activity.onReceive(context, intent);
        }
    }
}
