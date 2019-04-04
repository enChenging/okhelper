[okhelper](https://github.com/enChenging/okhelper)是对okhttp3的封装，为了使实际开发过程中对网络请求的使用更加的快捷、方便，所以本人在闲暇时对okhttp3进行了封装。[github地址:https://github.com/enChenging/okhelper](https://github.com/enChenging/okhelper)
## 用法

>Android Studio
 在build.gradle文件中的dependencies下添加引用：
	
```java
	implementation 'com.github.enChenging:okhelper:1.0.2'
```

## 配置OkhttpClient

> 默认使用okhttpClient的原始配置，如果需要对OkhttpClient配置可以在Application下进行配置：

```java
	 OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggingInterceptor())
                .hostnameVerifier(HttpsSSL.getHostnameVerifier())
                .sslSocketFactory(HttpsSSL.getSSLSocketFactory())
                .build();

        OkHelper.initClient(okHttpClient);
```

## 对于Log
>提供了一个LoggingInterceptor拦截器，可以自定义拦截器

```java
 .addInterceptor(new LoggingInterceptor())
```

## 对于Https

* 提供了HttpsSSL类
* 不验证直接通过所有的https连接,调用getSSLSocketFactory() 方法

```java
.sslSocketFactory(HttpsSSL.getSSLSocketFactory())
```
* 带证书验证，调用getSSLSocketFactory(Context context, String fileName)方法，传入assets文件夹下的证书名称

```java
.sslSocketFactory(HttpsSSL.getSSLSocketFactory(getApplicationContext(),"xxx.cer"))
```

### get请求

```java
 OkHelper.get("url").execute(mICallback);
 private ICallback mICallback = new StringCallBack() {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e(TAG, "onError: " + e);
        }

        @Override
        public void onResponse(String response, int id) {
            mTv.setText(response);
        }
    };
```
### getHttps请求

```java
OkHelper.get("url").execute(mICallback);
```
### getImage请求
```java
OkHelper.get("url").execute(new BitmapCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Bitmap response, int id) {
                mImageView.setImageBitmap(response);
            }
        });
```

### post请求

```java
  OkHelper.post("url")
          .param("key", "value")
          .execute(mICallback);

```

### postString请求

```java
   OkHelper.postString("url")
                .header("key", "value")
                .header("key", "value")
                .content("json")
                .execute(mICallback);
```

### postFile上传文件

```java
 File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        OkHelper.postFile("url")
                .file(file)
                .execute(mICallback);
```
将文件作为请求体，发送到服务器。


### uploadFile上传文件

```java
  File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        Map<String, String> params = new HashMap<>();
        params.put("key", "value");
        params.put("key", "value");

        Map<String, String> headers = new HashMap<>();
        headers.put("key", "value");
        headers.put("key", "value");

        OkHelper.post("url")
                .file("file", "test.txt", file)
                .params(params)
                .headers(headers)
                .execute(mICallback);
```

### multiFileUpload多文件上传

```java
File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test2.txt");
        Map<String, String> params = new HashMap<>();
        params.put("key", "value");
        params.put("key", "value");

        OkHelper.post("url")
                .file("file", "test.txt", file)
                .file("file", "test2.txt", file2)
                .params(params)
                .execute(mICallback);

```


### 文件下载

```java
        OkHelper.get("url")
                .execute(new FileCallBack(Environment.getExternalStorageDirectory() + "/downloadFile/", "test.apk") {


                    @Override
                    public void inProgress(float progress, long total) {
                        mProgressBar.setProgress((int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: " + response.getAbsolutePath());
                    }
                });
```


## 混淆

```java
#okhttputils
-dontwarn com.release.okhelper.**
-keep class com.release.okhelper.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

```

声明
-
应用中展示的所有内容均搜集自互联网，若内容有侵权请联系作者进行删除处理。本应用仅用作分享与学习。

关于作者
-
[CSDN博客：https://blog.csdn.net/AliEnCheng/article/details/89031855](https://blog.csdn.net/AliEnCheng/article/details/89031855)






