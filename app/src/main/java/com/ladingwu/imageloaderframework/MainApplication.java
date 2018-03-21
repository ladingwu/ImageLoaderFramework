package com.ladingwu.imageloaderframework;

import android.app.Application;

import com.ladingwu.frescolibrary.FrescoImageLoader;
import com.ladingwu.glidelibrary.GlideImageLocader;
import com.lasingwu.baselibrary.ImageLoaderConfig;
import com.lasingwu.baselibrary.ImageLoaderManager;
import com.lasingwu.baselibrary.LoaderEnum;


/**
 * Created by 拉丁吴 on 2017/6/25.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfig config = new ImageLoaderConfig
                .Builder(LoaderEnum.FRESCO,new FrescoImageLoader())
//                        .addImageLodaer(LoaderEnum.GLIDE,new GlideImageLocader())
                .maxMemory(40*1024*1024L)  // 单位为Byte
                .build();
        ImageLoaderManager.getInstance().init(this,config);

    }
}
