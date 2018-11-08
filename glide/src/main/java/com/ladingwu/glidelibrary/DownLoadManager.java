package com.ladingwu.glidelibrary;

import android.text.TextUtils;

import com.lasingwu.baselibrary.OnLoaderProgressCallback;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadManager {

    private static ConcurrentMap<String, OnLoaderProgressCallback> listeners =new ConcurrentHashMap<>();
    private static OkHttpClient okHttpClient;

    private DownLoadManager() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);
                            return response.newBuilder()
                                    .body(new ProgressResponseBody(request.url().toString(),LISTENER, response.body()))
                                    .build();
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }

    private static final ProgressResponseBody.OnProgressListener LISTENER = new ProgressResponseBody.OnProgressListener() {
        @Override
        public void onLoad(String url, int progress,boolean complete) {
            OnLoaderProgressCallback onProgressListener = getProgressListener(url);
            if (onProgressListener != null) {
                int percentage = (int) (progress);
                onProgressListener.onProgress(percentage);
                if (complete) {
                    listeners.remove(url);
                }
            }
        }
    };

    public static void addListener(String url, OnLoaderProgressCallback listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listeners.put(url, listener);
            listener.onProgress(0);
        }
    }


    private static OnLoaderProgressCallback getProgressListener(String url) {
        if (TextUtils.isEmpty(url) || listeners == null || listeners.size() == 0) {
            return null;
        }

        OnLoaderProgressCallback listenerWeakReference = listeners.get(url);
        if (listenerWeakReference != null) {
            return listenerWeakReference;
        }
        return null;
    }
}
