package com.release.okhelper.builder;

import com.release.okhelper.callBack.ICallback;
import com.release.okhelper.request.PostStringRequest;

import okhttp3.MediaType;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public class PostStringBuilder extends BaseBuilder<PostStringBuilder> {

    private String url;
    private String content;
    private MediaType mediaType;


    public PostStringBuilder(String url) {
        this.url = url;
    }

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }


    public void execute(ICallback callback) {
        new PostStringRequest(url, headers, content, mediaType).execute(callback);
    }

}
