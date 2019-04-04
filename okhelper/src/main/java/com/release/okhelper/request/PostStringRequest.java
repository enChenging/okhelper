package com.release.okhelper.request;

import com.release.okhelper.callBack.ICallback;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Mr.release
 * @create 2019/4/2
 * @Describe
 */
public class PostStringRequest extends BaseRequest {

    private String content;
    private MediaType mediaType;

    public PostStringRequest(String url, Map<String, String> headers, String content, MediaType mediaType) {
        super(url, headers);
        this.content = content;
        this.mediaType = mediaType;
    }

    @Override
    public RequestBody buildRequestBody() {
        if (mediaType == null)
            mediaType = MediaType.parse("text/plain;charset=utf-8");
        return RequestBody.create(mediaType, content);
    }

    @Override
    public RequestBody wrapRequestBody(RequestBody requestBody, ICallback callback) {
        return requestBody;
    }


    @Override
    public Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
}
