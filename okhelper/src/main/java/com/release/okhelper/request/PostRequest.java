package com.release.okhelper.request;

import com.release.okhelper.OkHelper;
import com.release.okhelper.builder.PostBuilder;
import com.release.okhelper.callBack.ICallback;

import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Mr.release
 * @create 2019/4/2
 * @Describe
 */
public class PostRequest extends BaseRequest {

    private List<PostBuilder.FileInput> files;
    private Map<String, String> params;

    public PostRequest(String url, Map<String, String> params, Map<String, String> headers, List<PostBuilder.FileInput> files) {
        super(url, headers);
        this.params = params;
        this.files = files;

    }

    @Override
    public RequestBody buildRequestBody() {
        if (files == null || files.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder, params);
            FormBody formBody = builder.build();
            return formBody;
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            addParams(builder, params);

            for (int i = 0; i < files.size(); i++) {
                PostBuilder.FileInput fileInput = files.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(getMediaType(fileInput.filename)), fileInput.file);
                builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
            }
            return builder.build();
        }
    }

    @Override
    public RequestBody wrapRequestBody(RequestBody requestBody, final ICallback callback) {
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
    public Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

}
