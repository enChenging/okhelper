package com.release.sample;

import android.app.Application;

import com.release.okhelper.OkHelper;
import com.release.okhelper.utils.HttpsSSL;
import com.release.okhelper.utils.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggingInterceptor())
                .hostnameVerifier(HttpsSSL.getHostnameVerifier())
                .sslSocketFactory(HttpsSSL.getSSLSocketFactory())
//                .sslSocketFactory(HttpsSSL.getSSLSocketFactory(getApplicationContext(),"xxx.cer"))
                .build();

        OkHelper.initClient(okHttpClient);
    }
}
