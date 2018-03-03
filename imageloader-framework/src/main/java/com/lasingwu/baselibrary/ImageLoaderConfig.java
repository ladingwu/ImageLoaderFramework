package com.lasingwu.baselibrary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhao on 2018/2/23.
 */

public class ImageLoaderConfig {
    private HashMap<LoaderEnum,IImageLoaderstrategy> imageloaderMap;
    private long maxMemory=0;
    private ImageLoaderConfig(Builder builder){
        imageloaderMap=builder.imageloaderMap;
        maxMemory=builder.maxMemory;
    }

    public long getMaxMemory() {
        return maxMemory <= 0 ? 40*1024*1024 : maxMemory;
    }

    public HashMap<LoaderEnum, IImageLoaderstrategy> getImageloaderMap() {
        return imageloaderMap;
    }

    public static class Builder{
        private HashMap<LoaderEnum,IImageLoaderstrategy> imageloaderMap =new HashMap<>();
        private long maxMemory=40*1024*1024;
        public Builder(LoaderEnum emun,IImageLoaderstrategy loaderstrategy){
            imageloaderMap.put(emun,loaderstrategy);
        }
        public Builder addImageLodaer(LoaderEnum emun,IImageLoaderstrategy loaderstrategy){
            imageloaderMap.put(emun,loaderstrategy);
            return this;
        }

        /**
         *
         * @param maxMemory 单位为 Byte
         * @return
         */
        public Builder maxMemory(Long maxMemory){
            this.maxMemory = maxMemory;
            return this;
        }
        public ImageLoaderConfig build(){
            return new ImageLoaderConfig(this);
        }
    }
}
