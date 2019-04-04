package com.release.okhelper.builder;

import com.release.okhelper.callBack.ICallback;
import com.release.okhelper.request.GetRequest;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public class GetBuilder extends BaseBuilder<GetBuilder> {

    private String url;

    public GetBuilder(String url) {
        this.url = url;
    }

    public void execute(ICallback callback) {
        new GetRequest(url, headers).execute(callback);
    }
}
