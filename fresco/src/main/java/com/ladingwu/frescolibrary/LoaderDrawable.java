package com.ladingwu.frescolibrary;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lasingwu.baselibrary.OnLoaderProgressCallback;

public class LoaderDrawable extends Drawable {
    private OnLoaderProgressCallback onLoaderProgressCallback;
    public LoaderDrawable(OnLoaderProgressCallback onLoaderProgressCallback){
        this.onLoaderProgressCallback = onLoaderProgressCallback;
    }
    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    protected boolean onLevelChange(int level) {
        if (onLoaderProgressCallback != null) {
            onLoaderProgressCallback.onProgress(level);
        }
        return true;
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
