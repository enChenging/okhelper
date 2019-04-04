package com.release.okhelper.callBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public abstract class ICallback<T> {

    public void onBefore(Request request) { }

    public void onAfter(int id) { }

    public void inProgress(float progress, long total) { }

    public boolean validateReponse(Response response, int id) {
        return response.isSuccessful();
    }

    public abstract T responseChange(Response response) throws IOException;

    public abstract void onError(Call call, Exception e, int id);

    public abstract void onResponse(T response, int id);


    public static ICallback CALLBACK_DEFAULT = new ICallback() {

        @Override
        public Object responseChange(Response response) throws IOException {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    };

}