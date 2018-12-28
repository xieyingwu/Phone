package com.ihoment.base.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ihoment.base.infra.LogInfra;
import com.ihoment.base.util.JsonUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by canxixie on 2017/9/27.
 */

public class MsgReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String classType = intent.getStringExtra("type");
            String body = intent.getStringExtra("body");
            LogInfra.Log.i("msg","process:"+body);
            EventBus.getDefault().post(JsonUtil.fromJson(body, Class.forName(classType)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
