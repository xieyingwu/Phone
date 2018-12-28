package com.ihoment.base.weak;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 文件描述：防止Activity泄露的Handler
 * 作者：damon
 * 创建日期：16/3/10
 */
public class WeakHandler extends Handler {
    private WeakReference<Callback> activityWeakReference;

    public WeakHandler(Callback cb) {
        activityWeakReference = new WeakReference(cb);
    }

    public WeakHandler(Looper looper, Callback cb) {
        super(looper);
        activityWeakReference = new WeakReference(cb);
    }

    @Override
    public void handleMessage(Message msg) {
        final Callback cb = activityWeakReference.get();
        if (cb != null) {
            cb.handleMessage(msg);
        }
    }
}