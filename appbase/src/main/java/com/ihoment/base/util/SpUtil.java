package com.ihoment.base.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xieyingwu on 2017/3/23.
 * 管理SharePreference存储
 */

public class SpUtil {
    public SpUtil(Context context, String spName) {
        initSp(context, spName);
    }

    private SharedPreferences sp;

    private void initSp(Context context, String spName) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public String getSpStr(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean getSpBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public int getSpInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void saveSpString(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void saveSpBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public void saveSpInt(String key, int value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

}
