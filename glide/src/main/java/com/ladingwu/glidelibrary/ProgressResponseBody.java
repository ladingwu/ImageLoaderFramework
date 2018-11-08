package com.ladingwu.glidelibrary;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.lasingwu.baselibrary.OnLoaderProgressCallback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {
    private OnProgressListener onLoaderProgressCallback;
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private String url;
    private static  Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    ProgressResponseBody(String url,OnProgressListener onLoaderProgressCallback, ResponseBody responseBody) {
        this.url = url;
        this.onLoaderProgressCallback = onLoaderProgressCallback;
        this.responseBody = responseBody;
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long total;
            long preReaded;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                total += (bytesRead == -1) ? 0 : bytesRead;

                if (onLoaderProgressCallback != null && preReaded != total) {
                    preReaded = total;
                    final float a = total/(contentLength()*1.0f);
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onLoaderProgressCallback.onLoad(url,(int) (a * 10000),a>=1);
                        }
                    });
                }
                return bytesRead;
            }
        };
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    public interface OnProgressListener{
        void onLoad(String url,int progress,boolean complete);
    }

}
