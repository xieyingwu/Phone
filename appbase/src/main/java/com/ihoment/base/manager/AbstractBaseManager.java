package com.ihoment.base.manager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xieyingwu on 2017/11/17.
 * 定义EventBus事件总线管理类
 */

public class AbstractBaseManager {
    private boolean isDestroy = false;

    public AbstractBaseManager() {
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
        }
    }

    protected boolean isDestroy() {
        return isDestroy;
    }

    public void destroy() {
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered) {
            EventBus.getDefault().unregister(this);
        }
        isDestroy = true;
    }
}
