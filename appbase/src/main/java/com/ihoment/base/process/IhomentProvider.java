package com.ihoment.base.process;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ihoment.base.infra.SharedPreManager;

/**
 * Created by canxixie on 2017/9/27.
 */

public class IhomentProvider extends ContentProvider {

    public static String SHARE_URL = "";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String dataType, @Nullable Bundle extras) {
        Bundle bundle = new Bundle();
        String key = extras.getString("key");
        String file = extras.getString("file");
        bundle.putBoolean("result", true);
        SharedPreManager.getInstance().init(getContext().getApplicationContext(),null,SHARE_URL);
        switch (ProcessConstants.MethodType.valueOf(method)) {
            case get: {
                switch (ProcessConstants.DataType.valueOf(dataType)) {
                    case typeBoolean: {
                        boolean value = SharedPreManager.getInstance().getBoolean(file, key, extras.getBoolean("value"));
                        bundle.putBoolean(key, value);
                        break;
                    }
                    case typeInt: {
                        int value = SharedPreManager.getInstance().getInt(file, key, extras.getInt("value"));
                        bundle.putInt(key, value);
                        break;
                    }
                    case typeLong: {
                        long value = SharedPreManager.getInstance().getLong(file, key, extras.getLong("value"));
                        bundle.putLong(key, value);
                        break;
                    }
                    case typeFloat: {
                        float value = SharedPreManager.getInstance().getFloat(file, key, extras.getFloat("value"));
                        bundle.putFloat(key, value);
                        break;
                    }
                    case typeString: {
                        String value = SharedPreManager.getInstance().getString(file, key, extras.getString("value"));
                        bundle.putString(key, value);
                        break;
                    }
                }
                break;
            }
            case save: {
                switch (ProcessConstants.DataType.valueOf(dataType)) {
                    case typeBoolean: {
                        SharedPreManager.getInstance().saveBoolean(file, key, extras.getBoolean("value"));
                        break;
                    }
                    case typeInt: {
                        SharedPreManager.getInstance().saveInt(file, key, extras.getInt("value"));
                        break;
                    }
                    case typeLong: {
                        SharedPreManager.getInstance().saveLong(file, key, extras.getLong("value"));
                        break;
                    }
                    case typeFloat: {
                        SharedPreManager.getInstance().saveFloat(file, key, extras.getFloat("value"));
                        break;
                    }
                    case typeString: {
                        SharedPreManager.getInstance().saveString(file, key, extras.getString("value"));
                        break;
                    }
                }
                break;
            }
            case remove: {
                SharedPreManager.getInstance().remove(file,key);
                break;
            }
        }

        return bundle;
    }
}
