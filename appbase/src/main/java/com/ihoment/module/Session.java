package com.ihoment.module;

import com.ihoment.base.infra.StorageInfra;

/**
 * Created by xieyingwu on 2017/4/20
 * session，用于记录登录状态
 */

public class Session {
    public String xid;
    public String token;

    public Session(String xid, String token) {
        this.xid = xid;
        this.token = token;
    }

    public static Session read() {
        return StorageInfra.get(Session.class);
    }

    public boolean write() {
        return StorageInfra.put(this);
    }

    /**
     * 判断是否登录
     *
     * @return true，表明登录成功
     */
    public static boolean isLogin() {
        return read() != null;
    }
}
