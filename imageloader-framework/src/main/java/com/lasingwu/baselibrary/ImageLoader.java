package com.lasingwu.baselibrary;

import android.support.annotation.NonNull;
import android.view.View;

public class ImageLoader {
    public static ImageLoaderOptions.Builder createImageOptions(@NonNull View v, @NonNull String url){
        return new ImageLoaderOptions.Builder(v, url);
    }
    public static ImageLoaderOptions.Builder createImageOptions(@NonNull View v, @NonNull int resource){
        return new ImageLoaderOptions.Builder(v, resource);
    }
}
