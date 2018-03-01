package com.ladingwu.frescolibrary;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lasingwu.baselibrary.IImageLoaderstrategy;
import com.lasingwu.baselibrary.ImageLoaderOptions;
import com.lasingwu.baselibrary.LoaderResultCallBack;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * Created by ${wuzhao} on 2017/10/18 0018.
 */

public class FrescoImageLoader implements IImageLoaderstrategy {
    @Override
    public void init(Context appContext) {
        Fresco.initialize(appContext, getPipelineConfig(appContext));
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options)   {
        showImgae(options);
    }

    @Override
    public void hideImage(@NonNull View view, int isVisiable) {

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



//    private ViewStatesListener mStatesListener;
    private static final int IMAGETAG=1;
    private void showImgae(final ImageLoaderOptions options) {
        ImageView imageView= (ImageView) options.getViewContainer();

        ViewGroup.LayoutParams params=imageView.getLayoutParams();
        if (params==null) {
            params=new ViewGroup.LayoutParams(200,200);
        }

        if (params.width==WRAP_CONTENT){
            params.width=MATCH_PARENT;
        }
        if (params.height==WRAP_CONTENT){
            params.height=MATCH_PARENT;
        }
        imageView.setLayoutParams(params);


        GenericDraweeHierarchy hierarchy=null;
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(imageView.getContext().getResources());
        DraweeHolder draweeHolder= (DraweeHolder) imageView.getTag(R.id.fresco_drawee);


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
        if (options.isCircle()) {
            RoundingParams roundingParams = new RoundingParams();
            hierarchyBuilder.setRoundingParams(roundingParams.setRoundAsCircle(true));
        }

        if (options.needImageRadius()) {
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(options.getImageRadius());
            hierarchyBuilder.setRoundingParams(roundingParams);
        }
        if (hierarchy == null) {
            hierarchy= hierarchyBuilder.build();

        }


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
            imageRequestBuilder.setPostprocessor(new BlurPostprocessor(options.getBlurValue()));
        }
        ImageRequest request =imageRequestBuilder.build();
        controllerBuilder.setImageRequest(request);
        if (options.getLoaderResultCallBack() != null) {
            controllerBuilder.setControllerListener(new ControllerListener<ImageInfo>() {
                @Override
                public void onSubmit(String id, Object callerContext) {

                }

                @Override
                public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                    LoaderResultCallBack callBack =options.getLoaderResultCallBack();
                    if (callBack != null) {
                        callBack.onSucc();

                    }
                }

                @Override
                public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                }

                @Override
                public void onIntermediateImageFailed(String id, Throwable throwable) {

                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    LoaderResultCallBack callBack =options.getLoaderResultCallBack();
                    if (callBack != null) {
                        callBack.onFail();

                    }

                }

                @Override
                public void onRelease(String id) {

                }
            });
        }
        DraweeController controller;

        if (draweeHolder == null) {
            draweeHolder=DraweeHolder.create(hierarchy,options.getViewContainer().getContext());
            controller=controllerBuilder.build();

        }else {
            controller= controllerBuilder.setOldController(draweeHolder.getController()).build();

        }

        // 请求
        draweeHolder.setController(controller);


        ViewStatesListener mStatesListener=new ViewStatesListener(draweeHolder);

        imageView.addOnAttachStateChangeListener(mStatesListener);

        // 判断是否ImageView已经 attachToWindow
        if (ViewCompat.isAttachedToWindow(imageView)) {
            draweeHolder.onAttach();
        }

//        if (ViewC.isAttachedToWindow()) {
//            draweeHolder.onAttach();
//        }
        // 保证每一个ImageView中只存在一个draweeHolder
        imageView.setTag(R.id.fresco_drawee,draweeHolder);
        // 拿到数据
        imageView.setImageDrawable(draweeHolder.getTopLevelDrawable());

    }


    public class ViewStatesListener implements View.OnAttachStateChangeListener{

        private DraweeHolder holder;
        public ViewStatesListener(DraweeHolder holder){
            this.holder=holder;
        }

        @Override
        public void onViewAttachedToWindow(View v) {
            this.holder.onAttach();
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            this.holder.onDetach();
        }
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
