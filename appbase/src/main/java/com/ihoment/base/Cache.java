package com.ihoment.base;

import com.ihoment.base.network.Network;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


public class Cache {
    public static Map<String, Object> caches = new HashMap<String, Object>();

    private Cache() {
    }

    //    public static <T> void put(Class<T> cls,Object  value){
//    	caches.put(cls.getName(), value);
//    }
    public static <T> T get(Class<T> cls) {
        Object object = caches.get(cls.getName());
        if (object == null) {
            instance(cls);
            object = caches.get(cls.getName());
        }
        return (T) object;
    }

    public static <T> T remove(Class<T> cls) {
        return (T) caches.remove(cls.getName());
    }

    private static void instance(Class clz) {
        String name = clz.getName();
        Object instance = null;
        if (name.endsWith("Net")) {
            /*针对单独一个网络请求*/
            instance = Network.getRetrofit().create(clz);
        } else {
            try {
                /*针对Manager处理的网络请求*/
                Class clazz = Class.forName(name.replace(".I", "."));
                Constructor cons = clazz.getDeclaredConstructor();
                cons.setAccessible(true);
                instance = cons.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        caches.put(clz.getName(), instance);
    }
}
