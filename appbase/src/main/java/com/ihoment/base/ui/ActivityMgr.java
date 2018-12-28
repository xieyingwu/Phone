package com.ihoment.base.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理类
 * <p>
 * Created by canxixie on 2017/9/28.
 */
public class ActivityMgr {

    private ArrayList<Activity> activities = new ArrayList<>();

    private boolean mIsAppForground;

    private AppStateListener mAppStateListener;

    public ArrayList<Activity> getActivities() {
        ArrayList<Activity> list = new ArrayList<Activity>();
        list.addAll(activities);
        return list;
    }

    private static class Builder {
        public static ActivityMgr instance = new ActivityMgr();
    }

    public static ActivityMgr getInstance() {
        return ActivityMgr.Builder.instance;
    }

    private ActivityMgr() {
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activities.size(); i < size; i++) {
            Activity activity = activities.get(i);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity，除了指定name的Activity
     *
     * @param name
     */
    public void finishAllActivityExcept(String name) {
        for (int i = 0, size = activities.size(); i < size; i++) {
            Activity activity = activities.get(i);
            if (activity != null && !activity.isFinishing() && !activity.getClass().getName().equals(name)) {
                activity.finish();
            }
        }
    }


    /**
     * 关闭指定的ACTIVITY
     *
     * @param name activityName
     */
    public void closeActivityByName(String name) {
        Activity activity = getActivity(name);
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    public void makesureSingleTask(Activity singleTaskActivity) {
        String className = singleTaskActivity.getClass().getName();
        for (int i = 0, size = activities.size(); i < size; i++) {
            Activity activityOld = activities.get(i);
            if (activityOld != null && activityOld.getClass().getName().equals(className)
                    && singleTaskActivity != activityOld) {
                activityOld.finish();
            }
        }
    }

    /**
     * 添加到集合中去
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activities.add(activity);
    }

    /**
     * 从集合中移除出去
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 根据Activity的classname，从集合中找到相应的Activity。注：因为是通过classname查找的，
     * 所以不保证能找到想要的Activity实例对象
     *
     * @param name
     * @return
     */
    public Activity getActivity(String name) {
        Activity activity = null;
        for (int i = 0, size = activities.size(); i < size; i++) {
            if (activities.get(i) != null && activities.get(i).getClass().getName().equals(name)) {
                activity = activities.get(i);
                break;
            }
        }
        return activity;
    }


    public Activity getActivityFromTop(String name) {
        Activity activity = null;
        int size = activities.size();
        for (int i = size - 1; i >= 0; i--) {
            if (activities.get(i) != null && activities.get(i).getClass().getName().equals(name)) {
                activity = activities.get(i);
                break;
            }
        }
        return activity;
    }

    public Activity getCurrentActivity(){
        if(activities.isEmpty()){
            return null;
        }
        return activities.get(activities.size()-1);
    }

    public <T> T getActivity(Class<T> clazz) {
        Activity activity = null;
        for (int i = 0, size = activities.size(); i < size; i++) {
            if (activities.get(i) != null && activities.get(i).getClass().equals(clazz)) {
                activity = activities.get(i);
                break;
            }
        }
        return (T) activity;
    }


    public void setAppStateListener(AppStateListener listener) {
        mAppStateListener = listener;
    }

    public void onStop(Activity baseActivity) {
        if (mAppStateListener != null) {
            if (mIsAppForground) {
                mIsAppForground = isAppForeground(baseActivity);
                if (!mIsAppForground) {
                    mAppStateListener.onAppBackground();
                }
            }
        }
    }

    public void onResume(Activity baseActivity) {
        if (mAppStateListener != null) {
            if (!mIsAppForground) {
                mIsAppForground = isAppForeground(baseActivity);
                if (mIsAppForground) {
                    mAppStateListener.onAppForeground();
                }
            }
        }
    }

    public interface AppStateListener {

        void onAppForeground();

        void onAppBackground();

    }

    public Activity getLastActivity() {
        int size = activities.size();
        if (size > 1) {
            return activities.get(size - 2);
        }
        return null;
    }


    private boolean isAppForeground(Context context) {
        String packageName = context.getApplicationInfo().packageName;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runinfo : runningAppProcesses) {
                String pn = runinfo.processName;
                if (pn.equals(packageName)
                        && runinfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                    return true;
            }
        }
        return false;
    }
}
