package com.release.okhelper.callBack;

import com.release.okhelper.OkHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * @author Mr.release
 * @create 2019/4/3
 * @Describe
 */
public abstract class FileCallBack extends ICallback<File> {

    private String fileDir;
    private String fileName;

    public FileCallBack(String fileDir, String fileName) {
        this.fileDir = fileDir;
        this.fileName = fileName;
    }


    @Override
    public File responseChange(Response response) throws IOException {
        return saveFile(response);
    }

    protected File saveFile(Response response) throws IOException {

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                OkHelper.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        inProgress(finalSum * 1.0f / total, total);
                    }
                });
            }
            fos.flush();

            return file;
        } finally {
            try {
                response.body().close();
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {

            }
        }
    }

}
