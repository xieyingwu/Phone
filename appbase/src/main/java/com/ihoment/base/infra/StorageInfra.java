package com.ihoment.base.infra;

import android.content.Context;
import android.text.TextUtils;

import com.ihoment.base.util.JsonUtil;

/**
 * https://github.com/pilgr/Paper
 * 更换存储库到Paper
 * 1.更快，使用Kryo进行序列化，https://github.com/EsotericSoftware/kryo，【注意：存储的对象必须提供无参数的构造方法】
 * 2.API支持分库
 * Created by wuwenlong on 3/12/16.
 */
public class StorageInfra {
    private static final String TAG = StorageInfra.class.getSimpleName();

    private static String DEFAULT_SCHEMA;

    /***
     * 数据存储在本地
     * @param context
     */
    public static void init(Context context) {
//        Paper.init(context);
    }


    public static void init(String schema) {
        DEFAULT_SCHEMA = schema;
//        Paper.init(dir);
    }


    /***
     * schema为库名，一个App可以有多个库，建议命名：{appname}.{name}，比如navigation.local，free.share
     * @param schema
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> boolean put(final String schema, final String key, final T value) {
        if (value == null) {
            return false;
        }
        String jsonValue = JsonUtil.toJson(value);
        SharedPreManager.getInstance().saveString(schema, key, jsonValue);
        return true;
    }

    public static <T> boolean put(T value) {
        if (value == null) {
            return false;
        }
        return put(DEFAULT_SCHEMA, value.getClass().getName(), value);
    }


    public static <T> boolean put(String schema, T value) {
        if (value == null) {
            return false;
        }
        return put(schema, value.getClass().getName(), value);
    }

    public static <T> T get(Class<T> clz) {
        return get(DEFAULT_SCHEMA, clz);
    }

    public static <T> T get(String schema, Class<T> clz) {

        String key = clz.getName();
        String jsonValue = SharedPreManager.getInstance().getString(schema, key, "");
        if (TextUtils.isEmpty(jsonValue)) {
            return null;
        }
        return JsonUtil.fromJson(jsonValue, clz);
    }

    public static <T> T get(String schema, String key, Class<T> clz) {
        String jsonValue = SharedPreManager.getInstance().getString(schema, key, "");
        if (TextUtils.isEmpty(jsonValue)) {
            return null;
        }
        return JsonUtil.fromJson(jsonValue, clz);
    }

    public static boolean remove(final String schema, final String key) {

        SharedPreManager.getInstance().remove(schema, key);
        return true;
    }

    public static boolean remove(Class clz) {
        return remove(DEFAULT_SCHEMA, clz.getName());
    }

    public static void destroy(final String schema) {
        SharedPreManager.getInstance().removeFile(schema);
    }
}
