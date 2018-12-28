package com.ihoment.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ihoment.base.Cache;
import com.ihoment.base.infra.LogInfra;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by canxixie on 2017/6/27.
 */
public class IhomeService extends Service {

    private static final String TAG = IhomeService.class.getSimpleName();


    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
            3);

    @Override
    public void onCreate() {
        LogInfra.Log.d(TAG, "IhomeService onCreate ...");
        super.onCreate();
        schedule();
    }

    private void schedule() {
        for (Class<? extends CommonTimerTask> taskClass : mTaskList) {
            CommonTimerTask task = Cache.get(taskClass);
            scheduledThreadPoolExecutor.scheduleAtFixedRate(task, task.getDelay(), task.getPeriod(), TimeUnit.MILLISECONDS);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogInfra.Log.d(TAG, "IhomeService onStartCommand ...");
        for (Class<? extends CommonTimerTask> taskClass : mTaskList) {
            CommonTimerTask task = Cache.get(taskClass);
            task.onStartCommand(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        for (Class<? extends CommonTimerTask> taskClass : mTaskList) {
            CommonTimerTask task = Cache.get(taskClass);
            task.onDestroy();
            Cache.remove(taskClass);
        }
        mTaskList.clear();
        super.onDestroy();
    }

    private static List<Class<? extends CommonTimerTask>> mTaskList = new ArrayList<>();

    public static void addTask(Class<? extends CommonTimerTask> task) {
        if (task != null) {
            mTaskList.add(task);
        }
    }

}
