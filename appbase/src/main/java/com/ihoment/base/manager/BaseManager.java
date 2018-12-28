package com.ihoment.base.manager;

import com.ihoment.base.network.BaseResponse;
import com.ihoment.base.network.ErrorResponse;
import com.ihoment.base.ui.Transactions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by canxixie on 2017/7/13.
 */

public class BaseManager extends AbstractBaseManager {
    protected Transactions transactions;


    public BaseManager() {
        super();
        transactions = new Transactions();
    }


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
