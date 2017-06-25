package com.example.ladingwu.imageloaderframework;

import android.app.Application;

import com.example.ladingwu.imageloaderframework.imageload.ImageLoaderManager;

/**
 * Created by 拉丁吴 on 2017/6/25.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderManager.getInstance().init(this);
    }
}
