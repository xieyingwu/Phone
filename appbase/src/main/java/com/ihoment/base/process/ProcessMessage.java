package com.ihoment.base.process;


import android.content.Intent;

import com.ihoment.base.util.JsonUtil;

/**
 * Created by canxixie on 2017/9/7.
 */


public class ProcessMessage {

    public String body;

    public String type;

    public Object msg;

    public ProcessMessage(Object message) {
        this.msg = message;
        this.body = JsonUtil.toJson(message);
        this.type = message.getClass().getName();
    }

    public Intent getMsgIntent() {
        Intent intent = new Intent();
        intent.putExtra("body", body);
        intent.putExtra("type", type);
        return intent;
    }

    public static ProcessMessage createMessage(Object message) {
        return new ProcessMessage(message);
    }
}
