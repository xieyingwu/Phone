package com.ihoment.base.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.ihoment.base.network.BaseResponse;
import com.ihoment.base.network.ErrorResponse;
import com.ihoment.base.weak.WeakHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 公共的BaseActivity
 *
 * @e-mail wenlong@oceanwing.com
 * @date 2016-02-23
 */
public abstract class BaseActivity extends AppCompatActivity implements android.os.Handler.Callback {
    protected String TAG;
    protected Transactions transactions;
    protected WeakHandler handler;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityMgr.getInstance().makesureSingleTask(this);
        ActivityMgr.getInstance().addActivity(this);
        TAG = this.getClass().getName();
        /*创建当前界面事件操作集合；用作EventBus事件响应判断*/
        transactions = new Transactions();
        setContentView(getLayout());
        /*采用ButterKnife来注解该Activity界面控件*/
        unbinder = ButterKnife.bind(this);
        /*注册EventBus来监听事件响应*/
        EventBus.getDefault().register(this);
        handler = new WeakHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
        handler.removeCallbacksAndMessages(null);
        ActivityMgr.getInstance().removeActivity(this);
    }

    protected abstract int getLayout();

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public void onReceive(Context context, Intent intent) {
    }

    //在此统一处理，如果需要单独处理，可Override
    protected void baseError(ErrorResponse vo) {
    }

    protected void baseResponse(BaseResponse vo) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVo(ErrorResponse vo) {
        if (!transactions.isMyTransaction(vo)) return;
        this.baseError(vo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVo(BaseResponse vo) {
        if (!transactions.isMyTransaction(vo)) return;
        this.baseResponse(vo);
    }
}
