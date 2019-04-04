package com.release.okhelper.request;

import com.release.okhelper.OkHelper;
import com.release.okhelper.callBack.ICallback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public abstract class BaseRequest {

    protected String url;
    protected Map<String, String> headers;
    private Request request;
    private Call call;


    protected Request.Builder builder = new Request.Builder();

    public BaseRequest(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;

        if (url == null)
            new IllegalArgumentException("url can not be null.");

        builder.url(url);
        appendHeaders();
    }


    private void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }


    public void execute(ICallback callback) {
        request = originalRequest(callback);
        call = OkHelper.getInstance().getOkHttpClient().newCall(this.request);

        if (callback != null) {
            callback.onBefore(request);
        }
        formalExecute(callback);
    }


    protected abstract RequestBody buildRequestBody();

    protected abstract RequestBody wrapRequestBody(RequestBody requestBody, ICallback callback);

    protected abstract Request buildRequest(RequestBody requestBody);

    private Request originalRequest(ICallback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    private void formalExecute(ICallback callback) {
        if (callback == null)
            callback = ICallback.CALLBACK_DEFAULT;
        final ICallback finalCallback = callback;

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFail(call, e, finalCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFail(call, new IOException("Canceled!"), finalCallback);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, 0)) {
                        sendFail(call, new IOException("request failed , code : " + response.code()), finalCallback);
                        return;
                    }

                    Object o = finalCallback.responseChange(response);
                    sendSuccess(o, finalCallback);
                } catch (Exception e) {
                    sendFail(call, e, finalCallback);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }

    private void sendFail(final Call call, final Exception e, final ICallback callback) {
        if (callback == null) return;

        OkHelper.getInstance().getPlatform().execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, 0);
                callback.onAfter(0);
            }
        });
    }

    private void sendSuccess(final Object o, final ICallback callback) {
        if (callback == null) return;
        OkHelper.getInstance().getPlatform().execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(o, 0);
                callback.onAfter(0);
            }
        });
    }

    protected void addParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }

    protected void addParams(MultipartBody.Builder builder, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
    }

    protected String getMediaType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
