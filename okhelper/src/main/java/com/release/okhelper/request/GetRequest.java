package com.release.okhelper.request;

import com.release.okhelper.callBack.ICallback;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Mr.release
 * @create 2019/4/2
 * @Describe
 */
public class GetRequest extends BaseRequest {

    public GetRequest(String url, Map<String, String> headers) {
        super(url, headers);
    }

    @Override
    public RequestBody buildRequestBody() {
        return null;
    }

    @Override
    public RequestBody wrapRequestBody(RequestBody requestBody, ICallback callback) {
        return requestBody;
    }

    @Override
    public Request buildRequest(RequestBody requestBody) {
        return builder.get().build();
    }
}
