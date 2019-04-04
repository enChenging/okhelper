package com.release.okhelper.builder;

import com.release.okhelper.callBack.ICallback;
import com.release.okhelper.request.PostFileRequest;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * @author Mr.release
 * @create 2019/4/2
 * @Describe
 */
public class PostFileBuilder extends BaseBuilder {
    private String url;
    private File file;
    private MediaType mediaType;
    protected Map<String, String> params;

    public PostFileBuilder(String url) {
        this.url = url;

    }

    public PostFileBuilder params(Map<String, String> params) {

        this.params = params;
        return this;
    }

    public PostFileBuilder param(String key, String val) {
        if (this.params == null)
            params = new LinkedHashMap<>();
        params.put(key, val);
        return this;
    }

    public PostFileBuilder file(File file) {
        this.file = file;
        return this;
    }

    public PostFileBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void execute(ICallback callback) {
        new PostFileRequest(url, params, headers, file, mediaType).execute(callback);
    }
}
