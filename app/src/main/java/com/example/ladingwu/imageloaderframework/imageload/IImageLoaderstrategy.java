package com.example.ladingwu.imageloaderframework.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * Created by Administrator on 2017/3/20 0020.
 */

public interface IImageLoaderstrategy {
    void showImage(@NonNull ImageLoaderOptions options);
    void cleanMemory(Context context);
    // 在application的oncreate中初始化
    void init(Context context);
}
