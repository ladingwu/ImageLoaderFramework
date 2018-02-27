package com.lasingwu.baselibrary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhao on 2018/2/23.
 */

public class ImageLoaderConfig {
    private HashMap<LoaderEnum,IImageLoaderstrategy> imageloaderMap;
    private ImageLoaderConfig(Builder builder){
        imageloaderMap=builder.imageloaderMap;
    }

    public HashMap<LoaderEnum, IImageLoaderstrategy> getImageloaderMap() {
        return imageloaderMap;
    }

    public static class Builder{
        private HashMap<LoaderEnum,IImageLoaderstrategy> imageloaderMap =new HashMap<>();
        public Builder(LoaderEnum emun,IImageLoaderstrategy loaderstrategy){
            imageloaderMap.put(emun,loaderstrategy);
        }
        public Builder addImageLodaer(LoaderEnum emun,IImageLoaderstrategy loaderstrategy){
            imageloaderMap.put(emun,loaderstrategy);
            return this;
        }
        public ImageLoaderConfig build(){
            return new ImageLoaderConfig(this);
        }
    }
}
