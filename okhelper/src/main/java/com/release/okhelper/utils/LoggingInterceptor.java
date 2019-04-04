package com.release.okhelper.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Mr.release
 * @create 2019/4/3
 * @Describe
 */
public class LoggingInterceptor implements Interceptor {

    private static final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.d(TAG, "intercept-request:" + request.url());
        Response response = chain.proceed(request);
        Log.d(TAG, "intercept-response:" + response.request().url());
        return response;
    }
}
