package com.ihoment.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by canxixie on 2017/10/9.
 */

public class BaseApplication extends MultiDexApplication {


    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }

    public static Context getContext() {
        return mBaseApplication.getApplicationContext();
    }

}
