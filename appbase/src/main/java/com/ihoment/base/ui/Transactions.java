package com.ihoment.base.ui;


import android.text.TextUtils;

import com.ihoment.base.infra.LogInfra.Log;
import com.ihoment.base.network.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenlong on 3/12/16.
 */
public class Transactions {
    private static final String TAG = Transactions.class.getSimpleName();
    private List<String> transacations;

    public Transactions() {
        transacations = new ArrayList<>();
    }

    /***
     * 发送event之前，创建一个标识符
     * @return
     */
    public synchronized String createTransaction() {
        String transaction = String.valueOf(System.currentTimeMillis());
        transacations.add(transaction);
        return transaction;
    }

    public synchronized boolean isMyTransaction(BaseResponse vo) {
        if (vo == null) return true;
        String transaction = vo.request.transaction;
        boolean flag = transaction == null || transaction.equals("") || transacations.remove(transaction);
        if (flag) {
            Log.i(TAG, vo);
        }
        return flag;
    }

    public synchronized boolean isMyTransactionWithoutLog(BaseResponse vo) {
        if (vo == null) return false;
        String transaction = vo.request.transaction;
        return !TextUtils.isEmpty(transaction) && transacations.remove(transaction);
    }

    public synchronized boolean isMyTransaction(String transaction) {
        return TextUtils.isEmpty(transaction) || transacations.remove(transaction);
    }

    /**
     * 清空transaction
     */
    public synchronized void clear() {
        transacations.clear();
    }
}
