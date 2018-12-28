package com.ihoment.base.cookie;

import java.io.IOException;
import java.util.Collection;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xieyingwu on 2017/4/10.
 * 读取Cookie的拦截器
 */

public class ReadCookieInterceptor implements Interceptor {
    final static String TAG = ReadCookieInterceptor.class.getName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        Request.Builder builder = chain.request().newBuilder();
        Cookie cookie = Cookie.read();
        Collection<String> values = cookie.cookies.values();
//        LogInfra.Log.w(TAG, "values.size() = " + values.size());
        for (String ck : values) {
            builder.addHeader("Cookie", ck);
        }
        builder.addHeader("Accept", "application/octet-stream")
                .addHeader("x-api-key", "m20xwttRNzBIKE8KP8wP5Mz7S61aSFa8x9cYOTU9");
        response = chain.proceed(builder.build());
        return response;
    }
}