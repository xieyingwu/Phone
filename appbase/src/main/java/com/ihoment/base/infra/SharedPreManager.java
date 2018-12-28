package com.ihoment.base.infra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.ihoment.base.process.ProcessSharePreManager;
import com.ihoment.base.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by canxixie on 2017/6/26.
 */
public class SharedPreManager {


    private static class Builder {
        public static SharedPreManager instance = new SharedPreManager();
    }

    public static SharedPreManager getInstance() {
        return Builder.instance;
    }


    private Map<String, SharedPreferences> sharedPreMap = new HashMap<String, SharedPreferences>();

    private SharedPreferences defaultSharedPreferences;

    private Context mContext;

    private String defaultFile;

    private boolean isChildProcess;

    private boolean isInit = false;
    private SharedPreManager() {
    }


    public void init(Context context, String defaultFileName, String url) {
        if(isInit){
            return;
        }
        isInit = true;
        mContext = context;
        defaultFile = defaultFileName;
        ProcessSharePreManager.getInstance().init(context, url);
        isChildProcess = !SystemUtil.isMainProcess(mContext);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getBoolean(null, key, defValue);
    }


    public boolean getBoolean(String fileName, String key, boolean defValue) {
        if (isChildProcess) {
            return ProcessSharePreManager.getInstance().getBooleanFile(fileName, key, defValue);
        }
        return getSharedPreferences(fileName).getBoolean(key, defValue);
    }


    public int getInt(String key, int defValue) {
        return getInt(null, key, defValue);
    }

    public int getInt(String fileName, String key, int defValue) {
        if (isChildProcess) {
            return ProcessSharePreManager.getInstance().getIntFile(fileName, key, defValue);
        }
        return getSharedPreferences(fileName).getInt(key, defValue);
    }

    public float getFloat(String fileName, String key, float defValue) {
        if (isChildProcess) {
            return ProcessSharePreManager.getInstance().getFloatFile(fileName, key, defValue);
        }
        return getSharedPreferences(fileName).getFloat(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getFloat(null, key, defValue);
    }

    public String getString(String key, String defValue) {
        return getString(null, key, defValue);
    }


    public String getString(String fileName, String key, String defValue) {
        if (isChildProcess) {
            return ProcessSharePreManager.getInstance().getStringFile(fileName, key, defValue);
        }

        String data = getSharedPreferences(fileName).getString(key, "");
        if (TextUtils.isEmpty(data)) {
            data = defValue;
        }
        return data;
    }

    public long getLong(String fileName, String key, long defValue) {
        if (isChildProcess) {
            return ProcessSharePreManager.getInstance().getLongFile(fileName, key, defValue);
        }
        return getSharedPreferences(fileName).getLong(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return getLong(null, key, defValue);
    }


    public void saveBoolean(String key, boolean value) {
        saveBoolean(null, key, value);
    }


    public void saveBoolean(String fileName, String key, boolean value) {

        if (isChildProcess) {
            ProcessSharePreManager.getInstance().saveBooleanFile(fileName, key, value);
            return;
        }
        getSharedPreferences(fileName).edit().putBoolean(key, value).commit();
    }


    public void saveInt(String key, int value) {
        saveInt(null, key, value);
    }

    public void saveInt(String fileName, String key, int value) {
        if (isChildProcess) {
            ProcessSharePreManager.getInstance().saveIntFile(fileName, key, value);
            return;
        }
        getSharedPreferences(fileName).edit().putInt(key, value).commit();
    }


    public void saveLong(String key, Long value) {
        saveLong(null, key, value);
    }

    public void saveLong(String fileName, String key, Long value) {
        if (isChildProcess) {
            ProcessSharePreManager.getInstance().saveLongFile(fileName, key, value);
            return;
        }
        getSharedPreferences(fileName).edit().putLong(key, value).commit();
    }


    public void saveFloat(String key, Float value) {
        saveFloat(null, key, value);
    }

    public void saveFloat(String fileName, String key, Float value) {
        if (isChildProcess) {
            ProcessSharePreManager.getInstance().saveFloatFile(fileName, key, value);
            return;
        }

        getSharedPreferences(fileName).edit().putFloat(key, value).commit();
    }


    public void saveString(String key, String value) {
        saveString(null, key, value);
    }

    public void saveString(String fileName, String key, String value) {
        if (isChildProcess) {
            ProcessSharePreManager.getInstance().saveStringFile(fileName, key, value);
            return;
        }
        getSharedPreferences(fileName).edit().putString(key, value).commit();
    }

    public boolean remove(String key) {
        if (isChildProcess) {
            return ProcessSharePreManager.getInstance().remove(key);
        }
        return remove(null, key);
    }

    public boolean remove(String fileName, String key) {
        if (isChildProcess) {
            return ProcessSharePreManager.getInstance().removeFile(fileName, key);
        }
        return getSharedPreferences(fileName).edit().remove(key).commit();
    }

    private SharedPreferences getSharedPreferences(String fileName) {

        if (TextUtils.isEmpty(fileName)) {

            if (TextUtils.isEmpty(defaultFile)) {
                if (defaultSharedPreferences == null) {
                    defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                }
                return defaultSharedPreferences;
            }
            fileName = defaultFile;
        }
        SharedPreferences pref = sharedPreMap.get(fileName);
        if (pref == null) {
            pref = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            sharedPreMap.put(fileName, pref);
        }
        return pref;
    }


    public void removeFile(String file) {
        getSharedPreferences(file).edit().clear().apply();
    }
}
