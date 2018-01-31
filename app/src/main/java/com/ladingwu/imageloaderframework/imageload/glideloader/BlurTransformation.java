package com.ladingwu.imageloaderframework.imageload.glideloader;

import android.graphics.Bitmap;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.ladingwu.imageloaderframework.imageload.BitmapUtils;

import java.security.MessageDigest;

/**
 * Created by wuzhao on 2018/1/31 0031.
 */

public class BlurTransformation extends BitmapTransformation {
    private static final String ID = BlurTransformation.class.getName();
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private int defaultRadius=15;
    public BlurTransformation(@IntRange(from=0) int radius){
        defaultRadius=radius;
    }
    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return  BitmapUtils.fastBlur(toTransform,defaultRadius);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlurTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
