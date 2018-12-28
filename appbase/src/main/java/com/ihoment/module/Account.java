package com.ihoment.module;


import android.text.TextUtils;

import com.ihoment.base.infra.StorageInfra;

/**
 * Created by wuwenlong on 20/04/2017
 */

public class Account {
    public String name;
    public String email;
    public String password;
    public String avatar;
    public String account_name;
    public String account_avatar;
    public String sign;
    public String sse;
    public String inbox;

    public Account() {

    }

    public Account(String name, String email, String inbox, String password, String avatar, String account_name, String account_avatar, String sign, String sse) {
        this.name = name;
        this.email = email;
        this.inbox = inbox;
        this.password = password;
        this.avatar = avatar;
        this.account_name = account_name;
        this.account_avatar = account_avatar;
        this.sign = sign;
        this.sse = sse;
    }

    public static Account read() {
        return StorageInfra.get(Constants.SCHEMA_ACCOUNT, Account.class);
    }

    public static boolean isDeviceNameInit() {
        Account read = Account.read();
        return read != null && !TextUtils.isEmpty(read.name);
    }

    public boolean write() {
        return StorageInfra.put(Constants.SCHEMA_ACCOUNT, this);
    }
}
