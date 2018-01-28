package com.example.ladingwu.imageloaderframework.imageload.glideloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.ladingwu.imageloaderframework.imageload.BitmapUtils;
import com.example.ladingwu.imageloaderframework.imageload.IImageLoaderstrategy;
import com.example.ladingwu.imageloaderframework.imageload.ImageLoaderOptions;

/**
 * Created by wuzhao on 2018/1/28.
 */

public class Glide4ImageLocader implements IImageLoaderstrategy {
    private Handler mainHandler = new Handler();

    @Override
    public void showImage(@NonNull final ImageLoaderOptions options) {
        RequestOptions requestOptions = new RequestOptions();

        if (options.getHolderDrawable() != -1) {
            requestOptions.placeholder(options.getHolderDrawable());
        }
        if (options.getErrorDrawable() != -1) {
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
        if (options.isSkipMemoryCache()) {
            requestOptions.skipMemoryCache(true);
        }
        if (options.getImageSize() != null) {
            requestOptions.override(options.getImageSize().getWidth(), options.getImageSize().getHeight());
        }

        RequestBuilder builder = getRequestBuilder(options);
        builder.listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {

                if (options.getLoaderResultCallBack() != null) {
                    options.getLoaderResultCallBack().onFail();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                if (options.getLoaderResultCallBack() != null) {
                    options.getLoaderResultCallBack().onSucc();
                }
                return false;
            }
        });
        if (options.isBlurImage()) {
            final ImageView img = (ImageView) options.getViewContainer();
            builder.apply(requestOptions).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition transition) {
                    if (img != null) {
                        try {
                            final Bitmap result = BitmapUtils.fastBlur(img.getContext().getApplicationContext(), resource, 100);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (result != null && img != null) {
                                        img.setImageBitmap(result);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            if (img != null) {
                                img.setImageBitmap(resource);
                            }
                        }


                    } else {
                        Log.e("imageloader", "resource null");
                    }
                }

            });
            return;
        }
        builder.apply(requestOptions).into((ImageView) options.getViewContainer());
    }

    private RequestManager getRequestManager(View view) {
        return Glide.with(view);

    }

    private RequestBuilder getRequestBuilder(ImageLoaderOptions options) {
        RequestBuilder builder = null;
        if (options.isAsGif()) {
            builder = getRequestManager(options.getViewContainer()).asGif();
        } else {
            builder = getRequestManager(options.getViewContainer()).asBitmap();
        }

        if (!TextUtils.isEmpty(options.getUrl())) {
            builder.load(options.getUrl());
        } else {
            builder.load(options.getResource());
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
