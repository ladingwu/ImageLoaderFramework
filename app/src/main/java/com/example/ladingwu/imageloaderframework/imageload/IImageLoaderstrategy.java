package com.example.ladingwu.imageloaderframework.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import cn.loveshow.live.manager.ImageLoadCallback;
import cn.loveshow.live.manager.ImageLoader;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public interface IImageLoaderstrategy {
    void showImage(@NonNull ImageLoaderOptions options);
    void cleanMemory(Context context);
    void init(Context context);
}
