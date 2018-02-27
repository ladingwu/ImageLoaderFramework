package com.ladingwu.frescolibrary;

import android.graphics.Bitmap;
import android.util.Log;

import com.facebook.imagepipeline.request.BasePostprocessor;
import com.lasingwu.baselibrary.BitmapUtils;


/**
 * Created by ${wuzhao} on 2017/6/22 0022.
 */

public class BlurPostprocessor extends BasePostprocessor {
    private int mRadius;

    public BlurPostprocessor(int blurRadius) {
        this.mRadius = blurRadius;
    }

    @Override
    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        Bitmap result=null;
        try {
            result= BitmapUtils.fastBlur(sourceBitmap, mRadius);
        } catch (Exception e) {
            e.printStackTrace();
            result=sourceBitmap;
        }finally {
            super.process(destBitmap, result);
        }
    }

    @Override
    public void process(Bitmap bitmap) {
        try {
            try {
                Bitmap result = BitmapUtils.fastBlur(bitmap, mRadius);
                super.process(result);
            } catch (OutOfMemoryError e) {
                Log.e("imageloader","OOM ...");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return "FastBlurPostprocessor";
    }
}
