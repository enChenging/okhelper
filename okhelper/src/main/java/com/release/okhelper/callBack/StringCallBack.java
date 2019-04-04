package com.release.okhelper.callBack;

import java.io.IOException;

import okhttp3.Response;

/**
 * @author Mr.release
 * @create 2019/4/3
 * @Describe
 */
public abstract class StringCallBack extends ICallback<String> {

    @Override
    public String responseChange(Response response) throws IOException {
        return response.body().string() + "";
    }
}
