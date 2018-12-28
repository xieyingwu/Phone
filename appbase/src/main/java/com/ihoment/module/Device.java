package com.ihoment.module;

import com.ihoment.base.infra.StorageInfra;

import java.util.List;

/**
 * 这个对象需要存储在不销毁的SCHEMA中，
 * Created by wuwenlong on 20/04/2017.
 */
public class Device {
    public String serial;
    public String uuid;
    public String server_public_key;
    public String device_private_key;
    public String version_hard;
    public String version_soft;
    public boolean auto_answer_call;
    public String type;
    public String id;
    public String gcmToken;
    public List<DeviceModel> models;// 账号下登录过哪些型号的pad

    public enum DeviceType {
        Device, Android, Ios
    }

    public static class DeviceModel {
        public String name;// "111"
        public String model;// "H7101"
        public String client;// "1e9fd15d16974e50b8e63a26c7731792"
    }

    public Device(DeviceType type, String serial, String server_public_key, String device_private_key, String version_hard) {
        this.type = type.name();
        this.serial = serial;
        this.server_public_key = server_public_key;
        this.device_private_key = device_private_key;
        this.version_hard = version_hard;
    }

    public static Device read() {
        Device device = StorageInfra.get(Constants.SCHEMA_TABLET, Device.class);
        if (device != null && (device.type == null || device.type.equals("")))
            device.type = DeviceType.Android.name();
        return device;
    }

    public boolean write() {
        return StorageInfra.put(Constants.SCHEMA_TABLET, this);
    }

    public static boolean isDeviceInit() {
        return Device.read() != null;
    }

    /***
     * 是否是我们自己的设备
     * 如果是App，也会存储Device对象，但是不会init，只有serial，uuid属性
     * @return
     */
    public boolean auto_answer() {
        return version_hard != null && auto_answer_call;
    }
}
