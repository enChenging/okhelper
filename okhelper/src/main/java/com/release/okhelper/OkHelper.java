package com.release.okhelper;

import com.release.okhelper.builder.GetBuilder;
import com.release.okhelper.builder.PostBuilder;
import com.release.okhelper.builder.PostFileBuilder;
import com.release.okhelper.builder.PostStringBuilder;
import com.release.okhelper.utils.Platform;

import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public class OkHelper {


    public static OkHelper mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;


    private OkHelper(OkHttpClient okHttpClient) {
        if (okHttpClient == null)
            mOkHttpClient = new OkHttpClient();
        else
            mOkHttpClient = okHttpClient;

        mPlatform = Platform.get();
    }

    public static OkHelper initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHelper.class) {
                if (mInstance == null) {
                    mInstance = new OkHelper(okHttpClient);
                }
            }
        }

        return mInstance;
    }

    public static OkHelper getInstance() {
        return initClient(null);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public static GetBuilder get(String url) {
        return new GetBuilder(url);
    }

    public static PostBuilder post(String url) {
        return new PostBuilder(url);
    }

    public static PostStringBuilder postString(String url) {
        return new PostStringBuilder(url);
    }

    public static PostFileBuilder postFile(String url) {
        return new PostFileBuilder(url);
    }
}
