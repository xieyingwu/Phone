package com.ihoment.base.cookie;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by xieyingwu on 2017/4/10.
 * 存储Cookie的拦截器
 */

public class SaveCookieInterceptor implements Interceptor {
    private static final String TAG = SaveCookieInterceptor.class.getName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse;
        originalResponse = chain.proceed(chain.request());
        List<String> headers = originalResponse.headers("Set-Cookie");
        if (!headers.isEmpty()) {
            Cookie cookie = Cookie.read();
            for (String header : headers) {
//                LogInfra.Log.w(TAG, "header = " + header);
                cookie.updateCookie(header);
            }
//            LogInfra.Log.w(TAG, "cookie.size() = " + cookie.cookies.size());
            cookie.write();
        }
        return originalResponse;
    }
}
