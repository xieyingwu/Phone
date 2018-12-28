package com.ihoment.base.network;

/**
 * Created by wuwenlong on 3/9/16.
 */
public class BaseResponse {
    public static final int RES_OK = 200;

    public BaseRequest request;
    public String message;
    public String code;
    public int status;
    public String bodyMd5;

    public boolean isSuccess() {
        return status == RES_OK;
    }

    public ErrorResponse toError() {
        return new ErrorResponse(request, status, code, message);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": status=" + status + ", message=" + message + ", code=" + code;
    }
}
