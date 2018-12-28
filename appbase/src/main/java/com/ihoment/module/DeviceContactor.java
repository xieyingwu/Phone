package com.ihoment.module;

/**
 * Created by xieyingwu on 2017/9/19.
 */

public class DeviceContactor {
    public int id;
    public String name;
    public String avatar;
    public int account;
    public String sign;
    public String uuid;
    public Contactor.ContactorType type;
    public String status;
    public String call_me;
    public boolean is_privacy;
    public boolean is_not_disturb;
    public boolean is_baby_monitor;
    public boolean is_stop_monitor;

    public boolean isCannotPlay() {
        return (!is_baby_monitor && is_stop_monitor) || is_privacy;
    }

}
