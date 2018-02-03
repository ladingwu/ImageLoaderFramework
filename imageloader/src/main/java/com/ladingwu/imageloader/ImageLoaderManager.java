package com.ladingwu.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.ladingwu.imageloader.glideloader.GlideImageLocader;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL;


/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ImageLoaderManager {
    private static final ImageLoaderManager INSTANCE=new ImageLoaderManager();
    private  IImageLoaderstrategy loaderstrategy;
    private static final String GLIDE="glide";
    private static final String FRESCO="fresco";
    private HashMap<String,IImageLoaderstrategy> imageloaderMap=new HashMap<>();
    private LoaderEnum curLoader = LoaderEnum.WHATEVER;
    private ImageLoaderManager(){
    }
    public static ImageLoaderManager getInstance(){
        return INSTANCE;
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
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).showImage(options);
        }
    }
    public void showImage(@NonNull ImageLoaderOptions options,LoaderEnum loaderEnum) {
        if (getLoaderstrategy(loaderEnum) != null) {
            getLoaderstrategy(loaderEnum).showImage(options);
        }
    }

    public void hideImage(@NonNull View view, int visiable) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).hideImage(view,visiable);
        }
    }


    public void cleanMemory(Context context) {
        getLoaderstrategy(curLoader).cleanMemory(context);
    }

    public void pause(Context context) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).pause(context);
        }
    }

    public void resume(Context context) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).resume(context);
        }
    }
    public void setImageLoader(LoaderEnum loader) {
        curLoader=loader;
    }
    // 在application的oncreate中初始化
    public void init(Context context) {
//        loaderstrategy=new FrescoImageLoader2();
        try {
            IImageLoaderstrategy glideLoader  = (IImageLoaderstrategy) Class.forName("com.ladingwu.imageloader.glideloader.GlideImageLocader").newInstance();
            if (glideLoader != null) {
                glideLoader.init(context);
                imageloaderMap.put(GLIDE,glideLoader);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            IImageLoaderstrategy frescoLoader  = (IImageLoaderstrategy) Class.forName("com.ladingwu.imageloader.frescoloader.FrescoImageLoader").newInstance();
            if (frescoLoader != null) {
                frescoLoader.init(context);
                imageloaderMap.put(FRESCO,frescoLoader);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        loaderstrategy=new GlideImageLocader();
//        loaderstrategy.init(context);
    }
    private IImageLoaderstrategy getLoaderstrategy(LoaderEnum loaderEnum){
        if (loaderEnum== LoaderEnum.FRESCO) {
            return imageloaderMap.get(FRESCO);
        }else if (loaderEnum== LoaderEnum.GLIDE) {
            return imageloaderMap.get(GLIDE);
        }else {
            return imageloaderMap.get(GLIDE)==null ? imageloaderMap.get(FRESCO) : imageloaderMap.get(GLIDE);
        }
    }

}
