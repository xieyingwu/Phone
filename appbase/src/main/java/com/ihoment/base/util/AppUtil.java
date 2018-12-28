package com.ihoment.base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by pengxianglin on 2017/3/24.
 */

public class AppUtil {


    private static int heightPixels;
    private static int widthPixels;
    private static float density;
    private static boolean isInit;

    public static void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;
        density = displayMetrics.density;
        isInit = true;
    }


    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            PackageManager pm = context.getPackageManager();
            packageInfo = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return packageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static String getVersionCode(Context context) {
        try {
            return getPackageInfo(context).versionCode + ".0";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionName(Context context) {
        try {
            return getPackageInfo(context).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float getDpi(Context context) {
        if (!isInit) {
            init(context);
        }
        return density;
    }

    public static int getScreenHeight(Context context) {
        if (!isInit) {
            init(context);
        }
        return heightPixels;
    }

    public static int getScreenWidth(Context context) {
        if (!isInit) {
            init(context);
        }
        return widthPixels;
    }

    public static String getLocalLanguage() {
        return Locale.getDefault().getLanguage();
    }
}
