package com.release.okhelper.request;

import com.release.okhelper.OkHelper;
import com.release.okhelper.callBack.ICallback;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Mr.release
 * @create 2019/4/2
 * @Describe
 */
public class PostFileRequest extends BaseRequest {

    private MediaType mediaType;
    private File file;
    private Map<String, String> params;

    public PostFileRequest(String url, Map<String, String> params, Map<String, String> headers, File file, MediaType mediaType) {
        super(url, headers);
        this.params = params;
        this.file = file;
        this.mediaType = mediaType;
    }

    @Override
    protected RequestBody buildRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        addParams(builder, params);
        return RequestBody.create(MediaType.parse(getMediaType(file.getName())), file);
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final ICallback callback) {
        if (callback == null) return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {
                OkHelper.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten * 1.0f / contentLength, contentLength);
                    }
                });
            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
}
