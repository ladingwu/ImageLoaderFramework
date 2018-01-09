package com.example.ladingwu.imageloaderframework.imageload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.ladingwu.imageloaderframework.imageload.frescoloader.FrescoImageLoader;
import com.example.ladingwu.imageloaderframework.imageload.frescoloader.FrescoProxyImageLoader;
import com.example.ladingwu.imageloaderframework.imageload.glideloader.GlideImageLoader;


/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ImageLoaderManager {
    private static final ImageLoaderManager INSTANCE=new ImageLoaderManager();
    private  IImageLoaderstrategy loaderstrategy;
    private ImageLoaderManager(){
    }
    public static ImageLoaderManager getInstance(){
        return INSTANCE;
    }

    public  void setImageLoaderStrategy(IImageLoaderstrategy strategy){
        loaderstrategy=strategy;
    }

    /*
     *   可创建默认的Options设置，假如不需要使用ImageView ，
     *    请自行new一个Imageview传入即可
     *  内部只需要获取Context
     */
    public static ImageLoaderOptions getDefaultOptions(@NonNull View container,@NonNull String url){
        return new ImageLoaderOptions.Builder(container,url).isCrossFade(true).build();
    }

    public void showImage(@NonNull ImageLoaderOptions options) {
        if (loaderstrategy != null) {
            loaderstrategy.showImage(options);
        }
    }

    public void hideImage(@NonNull View view, int visiable) {
        if (loaderstrategy != null) {
            loaderstrategy.hideImage(view,visiable);
        }
    }


    public void cleanMemory(Context context) {
        loaderstrategy.cleanMemory(context);
    }

    public void pause(Context context) {
        if (loaderstrategy != null) {
            loaderstrategy.pause(context);
        }
    }

    public void resume(Context context) {
        if (loaderstrategy != null) {
            loaderstrategy.resume(context);
        }
    }

    // 在application的oncreate中初始化
    public void init(Context context) {
        loaderstrategy=new FrescoProxyImageLoader();
//        loaderstrategy=new GlideImageLoader();
        loaderstrategy.init(context);
    }

}
