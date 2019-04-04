package com.release.okhelper.builder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public abstract class BaseBuilder<T extends BaseBuilder> {

    protected Map<String, String> headers;

    public T headers(Map<String, String> headers) {

        this.headers = headers;
        return (T) this;
    }

    public T header(String key, String val) {
        if (this.headers == null)
            headers = new LinkedHashMap<>();
        headers.put(key, val);
        return (T) this;
    }
}
