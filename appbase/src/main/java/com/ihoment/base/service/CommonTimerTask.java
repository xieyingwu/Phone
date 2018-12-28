package com.ihoment.base.service;

import android.content.Context;

/**
 * Created by canxixie on 2017/6/27.
 */

public abstract class CommonTimerTask implements Runnable {

    public abstract void onStartCommand(Context context);

    public abstract void onDestroy();

    public abstract long getDelay();

    public abstract long getPeriod();
}
