package com.example.ladingwu.imageloaderframework.imageload;

import android.graphics.drawable.Drawable;
import android.support.annotation.AnyRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.bumptech.glide.request.target.BaseTarget;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class ImageLoaderOptions {
    private View viewContainer;  // 图片容器
    private String url;  // 图片地址
    private Integer resource;  // 图片地址
    private int holderDrawable;  // 设置展位图
    private ImageSize imageSize;  //设置图片的大小
    private int errorDrawable;  //是否展示加载错误的图片
    private boolean asGif=false;   //是否作为gif展示
    private boolean isCrossFade=true; //是否渐变平滑的显示图片,默认为true
    private  boolean isSkipMemoryCache = false; //是否跳过内存缓存
    private  DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.DEFAULT; //磁盘缓存策略
    private  boolean blurImage = false; //是否使用高斯模糊
    private BaseTarget target = null; //target
    private LoaderResultCallBack loaderResultCallBack;   // 返回图片加载结果

    private ImageLoaderOptions (Builder builder ){
        this.asGif=builder.asGif;
        this.errorDrawable=builder.errorDrawable;
        this.holderDrawable=builder.holderDrawable;
        this.imageSize=builder.mImageSize;
        this.isCrossFade=builder.isCrossFade;
        this.isSkipMemoryCache=builder.isSkipMemoryCache;
        this.mDiskCacheStrategy=builder.mDiskCacheStrategy;
        this.url=builder.url;
        this.resource=builder.resource;
        this.viewContainer=builder.mViewContainer;
        this.blurImage=builder.blurImage;
        this.target=builder.target;
        this.loaderResultCallBack=builder.loaderResultCallBack;
    }

    public LoaderResultCallBack getLoaderResultCallBack() {
        return loaderResultCallBack;
    }

    public BaseTarget getTarget() {
        return target;
    }

    public Integer getResource() {
        return resource;
    }

    public boolean isBlurImage() {
        return blurImage;
    }

    public View getViewContainer() {
        return viewContainer;
    }

    public String getUrl() {
        return url;
    }



    public int getHolderDrawable() {
        return holderDrawable;
    }



    public ImageSize getImageSize() {
        return imageSize;
    }


    public int getErrorDrawable() {
        return errorDrawable;
    }



    public boolean isAsGif() {
        return asGif;
    }


    public boolean isCrossFade() {
        return isCrossFade;
    }



    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }



    public DiskCacheStrategy getDiskCacheStrategy() {
        return mDiskCacheStrategy;
    }



    public final static  class Builder{

        private int holderDrawable=-1;  // 设置展位图
        private View mViewContainer;  // 图片容器
        private String url;  // 图片地址
        private Integer resource;  // 图片地址
        private ImageSize mImageSize;  //设置图片的大小
        private int errorDrawable=-1;  //是否展示加载错误的图片
        private boolean asGif=false;   //是否作为gif展示
        private boolean isCrossFade=false; //是否渐变平滑的显示图片
        private  boolean isSkipMemoryCache = false; //是否跳过内存缓存
        private  boolean blurImage = false; //是否使用高斯模糊
        private  DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.DEFAULT; //磁盘缓存策略
        private BaseTarget target = null; //target
        private LoaderResultCallBack loaderResultCallBack;   // 返回图片加载结果

        public Builder(@NonNull View v, @NonNull String url){
            this.url=url;
            this.mViewContainer=v;
        }
        public Builder(@NonNull View v, @NonNull Integer resource){
            this.resource=resource;
            this.mViewContainer=v;
        }

        public Builder placeholder(@DrawableRes int holderDrawable){
            this.holderDrawable=holderDrawable;
            return this;
        }
        public Builder isCrossFade(boolean isCrossFade){
            this.isCrossFade=isCrossFade;
            return this;
        }
        public Builder blurImage(boolean blurImage){
            this.blurImage=blurImage;
            return this;
        }
        public Builder isSkipMemoryCache(boolean isSkipMemoryCache){
            this.isSkipMemoryCache=isSkipMemoryCache;
            return this;

        }
        public Builder override(int width,int height){
            this.mImageSize=new ImageSize(width,height);
            return this;
        }
        public Builder asGif(boolean asGif){
            this.asGif=asGif;
            return this;
        }
        public Builder error(@DrawableRes int errorDrawable){
            this.errorDrawable=errorDrawable;
            return this;
        }
        public Builder error(LoaderResultCallBack resultCallBack){
            this.loaderResultCallBack=resultCallBack;
            return this;
        }
        public Builder target(BaseTarget target){
            this.target=target;
            return this;
        }
        public Builder diskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy){
            this.mDiskCacheStrategy=mDiskCacheStrategy;
            return this;

        }

        public ImageLoaderOptions build(){
            return  new ImageLoaderOptions(this);
        }


    }

    //对应重写图片size
    public final static class ImageSize{
        private int width=0;
        private int height=0;
        public ImageSize(int width, int heigh){
            this.width=width;
            this.height=heigh;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
    //对应磁盘缓存策略
    public enum DiskCacheStrategy{
        All,NONE,SOURCE,RESULT,DEFAULT
    }
}
