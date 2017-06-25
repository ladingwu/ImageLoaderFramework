package com.example.ladingwu.imageloaderframework.imageload;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;




/**
 * Created by ${wuzhao} on 2017/5/18 0018.
 */

public class FrescoImageLoader implements IImageLoaderstrategy {
    @Override
    public void init(Context appContext) {
        Fresco.initialize(appContext, getPipelineConfig(appContext));
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        showImgaeDrawee(options);
    }



    @Override
    public void cleanMemory(Context context) {
        Fresco.getImagePipeline().clearMemoryCaches();
    }



    private void showImgaeDrawee(ImageLoaderOptions options) {
        View view=options.getViewContainer();
        SimpleDraweeView drawee=null;
        Class clazz=null;
        GenericDraweeHierarchy hierarchy=null;
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(view.getContext().getResources());
//        if (view instanceof SquareRImageView) {
//            clazz= SquareRImageView.class;
//            drawee=getDraweeView(view,clazz);
//            if (drawee != null) {
//                drawee.setAspectRatio(1);
//            }
//        }else if (view instanceof CircleImageView){
//            clazz= CircleImageView.class;
//            drawee=getDraweeView(view,clazz);
//            hierarchyBuilder.setFadeDuration(400).setRoundingParams(RoundingParams.asCircle());
//        }else
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
            // 本地路径
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

            drawee.setHierarchy(hierarchy);

            PipelineDraweeControllerBuilder controllerBuilder=Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true);

            ImageRequestBuilder imageRequestBuilder= ImageRequestBuilder.newBuilderWithSource(uri);
            if (options.getImageSize() != null) {
                imageRequestBuilder.setResizeOptions(new ResizeOptions(getSize(options.getImageSize().getWidth(),view), getSize(options.getImageSize().getWidth(),view)));
            }
            if (options.isBlurImage()) {
//                imageRequestBuilder.setPostprocessor(new BlurPostprocessor(view.getContext().getApplicationContext(), 15));
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
                mDraweeView=exchangeChilde(parent,viewContainer,params);

            }else if(viewContainer.getParent() instanceof RelativeLayout){
                RelativeLayout parent= (RelativeLayout) viewContainer.getParent();
                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) viewContainer.getLayoutParams();
                layout.setLayoutParams(params);
                mDraweeView=exchangeChilde(parent,viewContainer,params);

            }else if(viewContainer.getParent() instanceof LinearLayout){
                LinearLayout parent= (LinearLayout) viewContainer.getParent();
                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) viewContainer.getLayoutParams();
                layout.setLayoutParams(params);
                exchangeView(parent,viewContainer,layout);
                layout.addView(viewContainer);
                mDraweeView=exchangeChilde(layout,viewContainer,params);

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

    private SimpleDraweeView exchangeChilde(ViewGroup parent, View testImageView, ViewGroup.LayoutParams layoutParams) {
        SimpleDraweeView draweeview =null;
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (testImageView.equals(parent.getChildAt(i))) {
                if (testImageView instanceof ImageView) {
                    ImageView img= (ImageView) testImageView;
                    img.setBackgroundDrawable(null);
                    img.setImageDrawable(null);

                }
                if (i+1<parent.getChildCount()) {
                    View child=parent.getChildAt(i+1);
                    if (child instanceof SimpleDraweeView) {
                        return (SimpleDraweeView) child;
                    }
                }
                draweeview=new SimpleDraweeView(testImageView.getContext());
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
                // 保证缓存达到一定条件就及时清除缓存
                .setBitmapMemoryCacheParamsSupplier(new BitmapMemoryCacheParamsSupplier((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)))
                .build();
    }

    /**
     * 获取资源尺寸
     *
     * @param resSize
     * @return 默认返回原始尺寸
     */
    private int getSize(int resSize, View container) {
        if (resSize <= 0) {
            return SimpleTarget.SIZE_ORIGINAL;
        } else {
            try {
                return container.getContext().getResources().getDimensionPixelOffset(resSize);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                Log.e("","Resources.NotFoundException  I got !!!");
                return resSize;
            }
        }
    }

}
