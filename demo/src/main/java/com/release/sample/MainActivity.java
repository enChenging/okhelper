package com.release.sample;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.release.okhelper.OkHelper;
import com.release.okhelper.callBack.BitmapCallBack;
import com.release.okhelper.callBack.FileCallBack;
import com.release.okhelper.callBack.ICallback;
import com.release.okhelper.callBack.StringCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTv;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
        initView();
    }

    private void initView() {
        mTv = findViewById(R.id.id_textview);
        mImageView = findViewById(R.id.id_imageview);
        mProgressBar = findViewById(R.id.id_progress);
        mProgressBar.setMax(100);
    }


    public void get(View view) {
        OkHelper.get("http://m2.qiushibaike.com/article/list/suggest?page=1").execute(mICallback);
    }

    public void getHttps(View view) {
        OkHelper.get("https://kyfw.12306.cn/otn/").execute(mICallback);
    }

    public void getImage(View view) {

        OkHelper.get("http://pic.qiushibaike.com/system/avtnew/2333/23331917/medium/20190326174624.webp").execute(new BitmapCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Bitmap response, int id) {
                mImageView.setImageBitmap(response);
            }
        });
    }

    public void post(View view) {
        if (view != null) {
            Toast.makeText(MainActivity.this, "请填写自己的url地址和请求参数", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHelper.post("请填写自己的url地址")
                .param("selectcode", "31")
                .execute(mICallback);
    }


    public void postString(View view) {

        if (view != null) {
            Toast.makeText(MainActivity.this, "请填写自己的url地址和请求参数", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHelper.postString("请求地址")
                .header("User-Type", "MOBILE")
                .header("Access-Token", "token")
                .content("json数据")
                .execute(mICallback);
    }

    public void postFile(View view) {
        if (view != null) {
            Toast.makeText(MainActivity.this, "请填写自己的url地址和需要上传的文件", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        OkHelper.postFile("上传地址")
                .file(file)
                .execute(mICallback);

    }


    public void uploadFile(View view) {
        if (view != null) {
            Toast.makeText(MainActivity.this, "请填写自己的url地址、请求参数和需要上传的文件", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        Map<String, String> params = new HashMap<>();
        params.put("userId", "001");
        params.put("userName", "小明");

        Map<String, String> headers = new HashMap<>();
        headers.put("User-Type", "MOBILE");
        headers.put("Access-Token", "token");

        OkHelper.post("上传文件地址")
                .file("file", "test.txt", file)
                .params(params)
                .headers(headers)
                .execute(mICallback);
    }


    public void multiFileUpload(View view) {
        if (view != null) {
            Toast.makeText(MainActivity.this, "请填写自己的url地址、请求参数和需要上传的文件", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test2.txt");
        Map<String, String> params = new HashMap<>();
        params.put("userId", "001");
        params.put("userName", "小明");

        OkHelper.post("上传文件地址")
                .file("file", "test.txt", file)
                .file("file", "test2.txt", file2)
                .params(params)//
                .execute(mICallback);
    }


    public void downloadFile(View view) {
        String url = "http://appbs.yqxiu.net/1.1.4/9600391.apk";
        OkHelper.get(url)
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
    }

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


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void needsPermission() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
