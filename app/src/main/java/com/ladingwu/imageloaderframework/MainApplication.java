package com.ladingwu.imageloaderframework;

import android.app.Application;

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
        ImageLoaderManager.getInstance().init(this
                ,new ImageLoaderConfig.Builder(LoaderEnum.GLIDE,new GlideImageLocader())
//                        .addImageLodaer(LoaderEnum.GLIDE,new GlideImageLocader())
                        .build());
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
    }
}
