package com.release.okhelper.callBack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * @author Mr.release
 * @create 2019/4/3
 * @Describe
 */
public abstract class BitmapCallBack extends ICallback<Bitmap> {

    @Override
    public Bitmap responseChange(Response response) {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }
}
