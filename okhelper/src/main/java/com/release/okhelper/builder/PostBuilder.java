package com.release.okhelper.builder;

import com.release.okhelper.callBack.ICallback;
import com.release.okhelper.request.PostRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.release
 * @date 2019/3/31/031
 */
public class PostBuilder extends BaseBuilder<PostBuilder> {

    private String url;
    private List<FileInput> files;
    private Map<String, String> params;

    public PostBuilder(String url) {
        this.url = url;
    }

    public PostBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public PostBuilder param(String key, String val) {
        if (this.params == null)
            params = new LinkedHashMap<>();
        params.put(key, val);
        return this;
    }

    public PostBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }


    public PostBuilder file(String name, String filename, File file) {
        if (this.files == null)
            this.files = new ArrayList<>();

        files.add(new FileInput(name, filename, file));
        return this;
    }


    public void execute(ICallback callback) {
        new PostRequest(url, params, headers, files).execute(callback);
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }
}
