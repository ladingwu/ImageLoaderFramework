package com.example.ladingwu.imageloaderframework.imageload.glideloader;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ladingwu.imageloaderframework.imageload.IImageLoaderstrategy;
import com.example.ladingwu.imageloaderframework.imageload.ImageLoaderOptions;

/**
 * Created by wuzhao on 2018/1/28.
 */

public class Glide4ImageLocader implements IImageLoaderstrategy {
    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        RequestOptions requestOptions = new RequestOptions();

        if (options.getHolderDrawable()!=-1){
            requestOptions.placeholder(options.getHolderDrawable());
        }
        if (options.getErrorDrawable()!=-1){
            requestOptions.fallback(options.getErrorDrawable());
        }

        if (options.getDiskCacheStrategy() != ImageLoaderOptions.DiskCacheStrategy.DEFAULT) {
            switch (options.getDiskCacheStrategy()) {
                case NONE:
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                    break;
                case All:
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                    break;
                case SOURCE:
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                    break;
                case RESULT:
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
                    break;
                default:
                    break;
            }
        }
        if (options.isSkipMemoryCache()){
            requestOptions.skipMemoryCache(true);
        }
        RequestBuilder builder= getRequestBuilder(options);
        builder.apply(requestOptions).into((ImageView) options.getViewContainer());
    }

    private RequestManager getRequestManager(View view) {
        return Glide.with(view);

    }

    private RequestBuilder getRequestBuilder(ImageLoaderOptions options) {
        RequestBuilder builder=null;
        if (options.isAsGif()) {
            builder = getRequestManager(options.getViewContainer()).asGif().load(options.getUrl());
        } else {
            builder = getRequestManager(options.getViewContainer()).asBitmap().load(options.getUrl());
        }
        return builder;

    }
    @Override
    public void hideImage(@NonNull View view, int visiable) {
        if (view != null) {
            view.setVisibility(visiable);
        }
    }

    @Override
    public void cleanMemory(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context).clearMemory();

        }
    }

    @Override
    public void pause(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resume(Context context) {
        Glide.with(context).resumeRequests();

    }

    @Override
    public void init(Context context) {

    }
}
