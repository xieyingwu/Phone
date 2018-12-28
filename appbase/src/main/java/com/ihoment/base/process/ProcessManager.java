package com.ihoment.base.process;

import android.content.Context;
import android.content.Intent;

import com.ihoment.base.infra.LogInfra;
import com.ihoment.base.manager.BaseManager;
import com.ihoment.base.util.SystemUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by canxixie on 2017/9/7.
 */

public class ProcessManager extends BaseManager {


    private static final String TAG = "ChildProcessManager";

    private static class Builder {
        public static ProcessManager instance = new ProcessManager();
    }

    public static ProcessManager getInstance() {
        return ProcessManager.Builder.instance;
    }

    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public void startChildProcess() {
        mContext.startService(new Intent(mContext, ChildProcessService.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(ProcessMessage message) {
        LogInfra.Log.i(TAG, "ProcessMessage:" + message.body);
        EventBus.getDefault().post(message.msg);
        Intent intent = message.getMsgIntent();
        if (SystemUtil.isMainProcess(mContext)) {
            LogInfra.Log.i(TAG, "broadcast action = com.ihoment.msg.child");
            intent.setClassName(mContext, ChildMsgReceiver.class.getName());
            intent.setAction("com.ihoment.msg.child");
        } else {
            LogInfra.Log.i(TAG, "broadcast action = com.ihoment.msg.main");
            intent.setClassName(mContext, MsgReceiver.class.getName());
            intent.setAction("com.ihoment.msg.main");
        }
        mContext.sendBroadcast(intent);

    }

}
