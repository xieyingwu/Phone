package com.ihoment.base.network;

import com.ihoment.base.cookie.ReadCookieInterceptor;
import com.ihoment.base.cookie.SaveCookieInterceptor;
import com.ihoment.base.infra.LogInfra;
import com.ihoment.base.util.JsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wuwenlong on 3/11/16.
 */
public class Network {
    private static Retrofit retrofit;

    private static Retrofit noTimeoutRetrofit;

    public static void initRetrofit(String url, boolean debug, Interceptor interceptor, long timeout_secs) {
        OkHttpClient.Builder builder = getUnsafeOkHttpClient().newBuilder().writeTimeout(timeout_secs, TimeUnit.SECONDS).readTimeout(timeout_secs, TimeUnit.SECONDS)
                .connectTimeout(timeout_secs, TimeUnit.SECONDS).followRedirects(true);
        if (interceptor != null) builder.addInterceptor(interceptor);
        if (debug) {
            HttpLoggingInterceptor interceptor_log = new HttpLoggingInterceptor();
            interceptor_log.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(interceptor_log);
        }
        builder.addInterceptor(new ReadCookieInterceptor());
        builder.addInterceptor(new SaveCookieInterceptor());
        OkHttpClient client = builder.build();
        client.dispatcher().setMaxRequestsPerHost(4);//设置同一个端口，最大请求并发数
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        //设置超时为120秒
        builder.writeTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS);
        OkHttpClient clientNoTimeout = builder.build();
        clientNoTimeout.dispatcher().setMaxRequestsPerHost(4);//设置同一个端口，最大请求并发数
        noTimeoutRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientNoTimeout)
                .build();


    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }


    public static Retrofit getNoTimeoutRetrofit() {
        return noTimeoutRetrofit;
    }


    public static class IHCallBack<T> implements Callback<T> {
        private static final String TAG = IHCallBack.class.getSimpleName();
        protected BaseRequest request;
        protected ResponseHandle<T> handler;

        public IHCallBack(BaseRequest request) {
            this.request = request;
            LogInfra.Log.i(TAG, " Request: " + JsonUtil.toJson(request));
        }

        public IHCallBack(BaseRequest request, ResponseHandle<T> handler) {
            this(request);
            this.handler = handler;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            Headers headers = response.headers();
            String bodyMd5 = headers.get("x-hmt-md5");
            LogInfra.Log.d(TAG, "bodyMd5 = " + bodyMd5);
            if (!response.isSuccessful()) {
                LogInfra.Log.e(TAG, " Response Failure " + response.code());
                return;
            }
            BaseResponse resp = (BaseResponse) response.body();
            resp.request = request;
            resp.bodyMd5 = bodyMd5;

            if (!resp.isSuccess()) {
                EventBus.getDefault().post(resp.toError());
                return;
            }
            if (this.handler != null) {
                this.handler.onResponse((T) resp);
            }
            EventBus.getDefault().post(resp);
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            LogInfra.Log.e(TAG, " Response Failure", t);
            EventBus.getDefault().post(ErrorResponse.networkBroken(request));
        }
    }

    public interface ResponseHandle<T> {
        void onResponse(T response);
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            /*域名验证器*/
            HostnameVerifier allHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.hostnameVerifier(allHostnameVerifier);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
