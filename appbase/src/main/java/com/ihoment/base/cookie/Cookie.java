package com.ihoment.base.cookie;

import android.text.TextUtils;

import com.ihoment.base.infra.StorageInfra;

import java.util.HashMap;

/**
 * Created by xieyingwu on 2017/5/16.
 * 存储cookies
 */

public class Cookie {
    public HashMap<String, String> cookies = new HashMap<>();

    public static Cookie read() {
        Cookie cookie = StorageInfra.get(Cookie.class);
        if (cookie == null) {
            cookie = new Cookie();
        }
        return cookie;
    }

    public void updateCookie(String header) {
        if (TextUtils.isEmpty(header)) return;
        int firstIndex = header.indexOf("=");
        if (firstIndex == -1) return;
        String key = header.substring(0, firstIndex);
        addCookie(key, header);
    }

    public String getValueByKey(String key){
        return cookies.get(key);
    }

    private void addCookie(String key, String cookie) {
        cookies.put(key, cookie);
    }



    public void write() {
        StorageInfra.put(this);
    }

}
