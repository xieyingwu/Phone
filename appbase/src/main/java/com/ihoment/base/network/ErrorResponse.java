package com.ihoment.base.network;

import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wuwenlong on 20/04/2017.
 */

public class ErrorResponse extends BaseResponse {
    private static final int NETWORK_BROKEN_STATUS = 1000;
    private static final int SESSION_INVALID_STATUS = 401;

    public ErrorResponse(BaseRequest request, int status, String code, String message) {
        this.request = request;
        this.status = status;
        this.code = code;
        this.message = message;
//        TODO: 2017/9/24 由于服务器返回的错误信息在code字段中，因此暂时赋值message为code字段值
        if (TextUtils.isEmpty(this.message)) {
            this.message = code;
        }
        if (isSessionInvalid()) {
            /*通知Session无效*/
            SessionInvalidEvent.sendSessionInvalidEvent();
        }
    }

    public static ErrorResponse networkBroken(BaseRequest request) {
        return new ErrorResponse(request, NETWORK_BROKEN_STATUS, null, "network error");
    }

    public boolean isNetworkBroken() {
        return this.status == NETWORK_BROKEN_STATUS;
    }

    public boolean isSessionInvalid() {
        return this.status == SESSION_INVALID_STATUS;
    }

    public static class SessionInvalidEvent {
        private SessionInvalidEvent() {
        }

        public static void sendSessionInvalidEvent() {
            EventBus.getDefault().post(new SessionInvalidEvent());
        }
    }
}
