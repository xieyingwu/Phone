package com.ihoment.base.process;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;


/**
 * Created by canxixie on 2017/9/11.
 */

public class ProcessSharePreManager {

    private  Uri contentUri;


    private static class Builder {
        public static ProcessSharePreManager instance = new ProcessSharePreManager();
    }

    public static ProcessSharePreManager getInstance() {
        return ProcessSharePreManager.Builder.instance;
    }

    private ProcessSharePreManager() {

    }

    private Context mContext;

    public void init(Context context, String uri) {
        mContext = context;
        contentUri= Uri.parse("content://"+uri);
    }


    private Bundle createBundle(String file, String key) {
        Bundle bundle = new Bundle();
        bundle.putString("file", file);
        bundle.putString("key", key);
        return bundle;
    }

    public void saveBooleanFile(String file, String key, boolean value) {
        Bundle bundle = createBundle(file, key);
        bundle.putBoolean("value", value);
        mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.save.name(),ProcessConstants.DataType.typeBoolean.name(), bundle);
    }

    public boolean getBooleanFile(String file, String key, boolean defaultValue) {
        Bundle bundle = createBundle(file, key);
        bundle.putBoolean("value", defaultValue);
        Bundle resultBundle = mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.get.name(),ProcessConstants.DataType.typeBoolean.name(), bundle);
        return resultBundle.getBoolean(key, defaultValue);
    }

    public void saveIntFile(String file, String key, int value) {
        Bundle bundle = createBundle(file, key);
        bundle.putInt("value", value);
        mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.save.name(), ProcessConstants.DataType.typeInt.name(), bundle);
    }

    public int getIntFile(String file, String key, int defaultValue) {
        Bundle bundle = createBundle(file, key);
        bundle.putInt("value", defaultValue);
        Bundle resultBundle = mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.get.name(), ProcessConstants.DataType.typeInt.name(), bundle);
        return resultBundle.getInt(key, defaultValue);
    }

    public void saveLongFile(String file, String key, long value) {
        Bundle bundle = createBundle(file, key);
        bundle.putLong("value", value);
        mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.save.name(), ProcessConstants.DataType.typeLong.name(), bundle);
    }

    public long getLongFile(String file, String key, long defaultValue) {
        Bundle bundle = createBundle(file, key);
        bundle.putLong("value", defaultValue);
        Bundle resultBundle = mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.get.name(), ProcessConstants.DataType.typeLong.name(), bundle);
        return resultBundle.getLong(key, defaultValue);
    }

    public void saveStringFile(String file, String key, String value) {
        Bundle bundle = createBundle(file, key);
        bundle.putString("value", value);
        mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.save.name(), ProcessConstants.DataType.typeString.name(), bundle);
    }

    public String getStringFile(String file, String key, String defaultValue) {
        Bundle bundle = createBundle(file, key);
        bundle.putString("value", defaultValue);
        Bundle resultBundle = mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.get.name(), ProcessConstants.DataType.typeString.name(), bundle);
        return resultBundle.getString(key, defaultValue);
    }

    public void saveFloatFile(String file, String key, float value) {
        Bundle bundle = createBundle(file, key);
        bundle.putFloat("value", value);
        mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.save.name(), ProcessConstants.DataType.typeFloat.name(), bundle);
    }

    public float getFloatFile(String file, String key, float defaultValue) {
        Bundle bundle = createBundle(file, key);
        bundle.putFloat("value", defaultValue);
        Bundle resultBundle = mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.get.name(), ProcessConstants.DataType.typeFloat.name(), bundle);
        return resultBundle.getFloat(key, defaultValue);
    }


    public boolean remove(String key) {
        return removeFile("",key);
    }

    public boolean removeFile(String fileName, String key) {
        Bundle bundle = createBundle(fileName, key);
        Bundle resultBundle = mContext.getContentResolver().call(contentUri, ProcessConstants.MethodType.remove.name(), "", bundle);
        return resultBundle.getBoolean("result",false);
    }
}
