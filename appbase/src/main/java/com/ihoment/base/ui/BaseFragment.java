package com.ihoment.base.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihoment.base.network.BaseResponse;
import com.ihoment.base.network.ErrorResponse;
import com.ihoment.base.weak.WeakHandler;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 通用的Fragment
 *
 * @e-mail wenlong@oceanwing.com
 * @date 2016-02-23
 */
public abstract class BaseFragment extends Fragment implements android.os.Handler.Callback {
    protected String TAG;
    protected WeakHandler handler;
    protected Transactions transactions;
    protected boolean isUnbind;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getName();
        handler = new WeakHandler(this);
        transactions = new Transactions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, layout);
        isUnbind = false;
        init();
        return layout;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isUnbind = true;
        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    protected abstract int getLayout();

    protected abstract void init();

    public void onReceive(Context context, Intent intent) {
    }

    protected void baseResponse(BaseResponse vo) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVo(BaseResponse vo) {
        if (!transactions.isMyTransaction(vo)) return;
        this.baseResponse(vo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVo(ErrorResponse vo) {
        if (!transactions.isMyTransaction(vo)) return;
//        if (vo.isNetworkBroken()) {
//            Toast.makeText(getContext(), "网络中断，显示WIFI设置导航", Toast.LENGTH_SHORT).show();
//            //TODO:网络中断，显示WIFI设置导航
//            return;
//        }

        this.baseError(vo);
    }

    //在此统一处理，如果需要单独处理，可Override
    protected void baseError(ErrorResponse vo) {
        // TODO: 2017/3/24 全局界面错误处理展示
//        Toast.makeText(getContext(), "访问出错" + vo.message, Toast.LENGTH_SHORT).show();
    }
}
