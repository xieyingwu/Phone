package com.ihoment.base.network;

import com.ihoment.base.Transaction;

/**
 * Created by wuwenlong on 3/9/16.
 */
public class BaseRequest extends Transaction {

    /**
     * view 用来区分请求类型
     */
    public int view;

    /**
     * key 用来区分某次请求
     */
    public String key = "";

    public BaseRequest(String transaction) {
        super(transaction);
    }

    public BaseRequest(String transaction, int view) {
        super(transaction);
        this.view = view;
    }

    public BaseRequest(String transaction, String key) {
        super(transaction);
        this.key = key;
    }

    public BaseRequest(String transaction, int view, String key) {
        super(transaction);
        this.view = view;
        this.key = key;
    }

    public static BaseRequest instance(String transaction) {
        return new BaseRequest(transaction);
    }

    public static BaseRequest instance(String transaction, int view) {
        return new BaseRequest(transaction, view);
    }
}
