package com.release.okhelper.callBack;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * @author Mr.release
 * @create 2019/4/3
 * @Describe
 */
public abstract class BeanCallBack<T> extends ICallback<T>{

    @Override
    public T responseChange(Response response) throws IOException {
        String body = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) return (T) body;
        return JSON.parseObject(body,entityClass);
    }

}
