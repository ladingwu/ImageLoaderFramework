package com.example.ladingwu.imageloaderframework.imageload.frescoloader;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ladingwu.imageloaderframework.imageload.IImageLoaderstrategy;
import com.example.ladingwu.imageloaderframework.imageload.ImageLoaderOptions;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * Created by ${wuzhao} on 2017/5/18 0018.
 */

public class FrescoImageLoader implements IImageLoaderstrategy {
    @Override
    public void init(Context appContext) {
        Fresco.initialize(appContext, getPipelineConfig(appContext));
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options)   {
        showImgaeDrawee(options);
    }

    @Override
    public void hideImage(@NonNull View view, int isVisiable) {
        SimpleDraweeView drawee=getDraweeView(view,ImageView.class);
        if (drawee != null) {
            drawee.setVisibility(isVisiable);
        }
        view.setVisibility(isVisiable);
    }


    @Override
    public void cleanMemory(Context context) {
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    @Override
    public void pause(Context context) {
        Fresco.getImagePipeline().pause();
    }

    @Override
    public void resume(Context context) {
        Fresco.getImagePipeline().resume();
    }


    // 检查ImageView的宽高
    private void checkWH(ImageLoaderOptions options) {
        View view =options.getViewContainer();
        ViewGroup.LayoutParams params=view.getLayoutParams();
        if (params == null) {
            return;
        }
        if (options.getImageSize()!=null) {
            if ( params.height==WRAP_CONTENT ){
                params.height=options.getImageSize().getHeight();
            }
            if (params.width==WRAP_CONTENT ){
                params.width=options.getImageSize().getWidth();
            }
        }

    }

    private void showImgaeDrawee(ImageLoaderOptions options) {
        View view=options.getViewContainer();
        SimpleDraweeView drawee=null;
        Class clazz=null;
        GenericDraweeHierarchy hierarchy=null;
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(view.getContext().getResources());

        if (view instanceof SimpleDraweeView){
            drawee= (SimpleDraweeView) view;
            hierarchy=drawee.getHierarchy();
        }else if(view instanceof ImageView){
            clazz= ImageView.class;
            drawee=getDraweeView(view,clazz);
        }
        else {
            Log.i("","no type !!");
            return;
        }

        if (drawee != null) {
            Uri uri=Uri.parse(options.getUrl());
            // 本地路径  处理
            if (options.getUrl() != null && !options.getUrl().contains("http")) {
                uri=Uri.parse("file://"+options.getUrl());
            }

            if (options.getHolderDrawable()!=-1) {
                hierarchyBuilder.setPlaceholderImage(options.getHolderDrawable());
            }
            if (options.getErrorDrawable()!=-1) {
                hierarchyBuilder.setFailureImage(options.getErrorDrawable());
            }

            if (hierarchy == null) {
                hierarchy= hierarchyBuilder.build();

            }
            drawee.setVisibility(View.VISIBLE);
            drawee.setHierarchy(hierarchy);

            PipelineDraweeControllerBuilder controllerBuilder=Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true);

            ImageRequestBuilder imageRequestBuilder= ImageRequestBuilder.newBuilderWithSource(uri);
            if (options.getImageSize() != null) {
                imageRequestBuilder.setResizeOptions(new ResizeOptions(options.getImageSize().getWidth(), options.getImageSize().getWidth()));
            }
            if (! options.isAsGif()) {
                // 解决有些gif格式的头像的展示问题，因为我们需要展示一个静态的圆形图片
                imageRequestBuilder.setImageDecodeOptions(ImageDecodeOptions.newBuilder().setForceStaticImage(true).build());
            }
            if (options.isBlurImage()) {
                imageRequestBuilder.setPostprocessor(new BlurPostprocessor(view.getContext().getApplicationContext(), 15));
            }
            ImageRequest request =imageRequestBuilder.build();
            controllerBuilder.setImageRequest(request);
            DraweeController controller=controllerBuilder.build();

            drawee.setController(controller);

        }
    }

    public Bitmap copyBitmap(Bitmap source){
        int width = source.getWidth();
        int height = source.getHeight();
        int scaledWidth = width / 1;
        int scaledHeight = height / 1;
        Bitmap blurredBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight,source.getConfig());
        if (source.getConfig()==blurredBitmap.getConfig()) {
            Bitmaps.copyBitmap(blurredBitmap,source);
        }else{
            Canvas canvas = new Canvas(blurredBitmap);
            canvas.drawBitmap(source,0,0,null);
        }
        return blurredBitmap;

    }

    private SimpleDraweeView getDraweeView(View viewContainer,Class<?> classType) {
        if (viewContainer instanceof SimpleDraweeView){
            return (SimpleDraweeView) viewContainer;
        }
        SimpleDraweeView mDraweeView=null;
        if (classType.isInstance(viewContainer)){
            FrameLayout layout=new FrameLayout(viewContainer.getContext());
            if(viewContainer.getParent() instanceof FrameLayout){
                FrameLayout parent= (FrameLayout) viewContainer.getParent();
                FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) viewContainer.getLayoutParams();
                layout.setLayoutParams(params);
                mDraweeView=addSimpleDrawee(parent,viewContainer,params);

            }else if(viewContainer.getParent() instanceof RelativeLayout){
                RelativeLayout parent= (RelativeLayout) viewContainer.getParent();
                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) viewContainer.getLayoutParams();
                layout.setLayoutParams(params);
                mDraweeView=addSimpleDrawee(parent,viewContainer,params);

            }else if(viewContainer.getParent() instanceof LinearLayout){
                LinearLayout parent= (LinearLayout) viewContainer.getParent();
                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) viewContainer.getLayoutParams();
                layout.setLayoutParams(params);
                exchangeView(parent,viewContainer,layout);
                layout.addView(viewContainer);
                mDraweeView=addSimpleDrawee(layout,viewContainer,params);

            }else{
                // 出现这种情况需要找到到底是其他的Layout还是null,然后做相应的处理，小爱项目基本只使用了上面三种布局
                ViewParent parent=viewContainer.getParent();
                Log.i("","parent exception");
            }
        }else{
        }
        return mDraweeView;
    }

    /*
     * 将ViewGroup中的一个childView移除，在同原样的位置添加一个新的View
     */
    private void exchangeView(ViewGroup parent, View viewOld, View viewNew){
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChildAt(i).equals(viewOld)) {
                parent.removeView(viewOld);
                parent.addView(viewNew,i);
                return;
            }
        }

    }

    private SimpleDraweeView addSimpleDrawee(ViewGroup parent, View imageView, ViewGroup.LayoutParams layoutParams) {
        SimpleDraweeView draweeview =null;
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (imageView.equals(parent.getChildAt(i))) {
                Drawable drawable=imageView.getBackground();
                if (imageView instanceof ImageView) {
                    ImageView img= (ImageView) imageView;
                    // 保留预设在ImageView上的 shape.xml 和selector.xml文件 的效果
                    if (!(drawable instanceof ShapeDrawable || drawable instanceof StateListDrawable)) {
                        img.setBackgroundDrawable(null);
                    }

                    img.setImageDrawable(null);

                }
                if (i+1<parent.getChildCount()) {
                    View child=parent.getChildAt(i+1);
                    if (child instanceof SimpleDraweeView) {
                        return (SimpleDraweeView) child;
                    }
                }
                draweeview=new SimpleDraweeView(imageView.getContext());
                draweeview.setLayoutParams(layoutParams);
                parent.addView(draweeview,i+1);
                return draweeview;
            }
        }
        return draweeview;
    }

    public ImagePipelineConfig getPipelineConfig(Context context) {
        // set the cache file path
        DiskCacheConfig diskCacheConfig=DiskCacheConfig.newBuilder(context)
                .setMaxCacheSize(30*1024*1024)
                .setMaxCacheSizeOnLowDiskSpace(5*1024*1024)
                .build();

        return ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                // 设置缓存
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                // 保证缓存达到一定条件就及时清除缓存
                .setBitmapMemoryCacheParamsSupplier(new BitmapMemoryCacheParamsSupplier((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)))
                .build();
    }

}
