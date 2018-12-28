package com.ihoment.module;

/**
 * Created by pengxianglin on 2017/5/6.
 */

public class Contactor {
    public int account;
    public String uuid;
    public String name;
    public String avatar;
    public String call_me;
    public String email;

    public ContactorType type;

    public enum ContactorType {
        Device, Android, Ios, Friend
    }

    public Contactor() {

    }

    public Contactor(int account, String uuid, String name, String avatar, ContactorType type){
        this.account=account;
        this.uuid=uuid;
        this.name=name;
        this.avatar=avatar;
        this.type=type;
    }
}
