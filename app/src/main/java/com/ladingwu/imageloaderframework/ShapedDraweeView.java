/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Credit:
 *  1. Shape mask is from: https://github.com/siyamed/android-shape-imageview
 *  2. Fresco custom view is from: http://fresco-cn.org/docs/writing-custom-views.html
 */

package com.ladingwu.imageloaderframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;

public class ShapedDraweeView extends AppCompatImageView {
    private static final String TAG = ShapedDraweeView.class.getSimpleName();

    private static final PorterDuffXfermode PORTER_DUFF_XFERMODE =
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    private Canvas maskCanvas;
    private Bitmap maskBitmap;

    private Canvas drawableCanvas;
    private Bitmap drawableBitmap;
    private Paint drawablePaint;

    private boolean invalidated = true;

    private Drawable shape;

    public ShapedDraweeView(Context context) {
        super(context);
        setup(context, null, 0);
    }

    public ShapedDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public ShapedDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs, defStyle);
    }

    private DraweeHolder<GenericDraweeHierarchy> mDraweeHolder;

    private void setup(Context context, AttributeSet attrs, int defStyle) {
        if (getScaleType() == ScaleType.FIT_CENTER) {
            setScaleType(ScaleType.CENTER_CROP);
        }

        Drawable placeholder = null;
//        if (attrs != null) {
//            TypedArray typedArray =
//                    context.obtainStyledAttributes(attrs, R.styleable.ShapedDrawee, defStyle, 0);
//            int shapeId = typedArray.getResourceId(R.styleable.ShapedDrawee_maskShape, -1);
//            // AppCompatResources is added in 24.2.0, for those don't use up to date support
//            // library, we could not use this class :(
//            // shape = AppCompatResources.getDrawable(getContext(), shapeId);
//            setImageResource(shapeId);
//            shape = getDrawable();
//            if (shape == null) {
//                throw new IllegalArgumentException("maskShape must be specified in layout!");
//            }
//            placeholder = typedArray.getDrawable(R.styleable.ShapedDrawee_placeholder);
//            typedArray.recycle();
//        }

        GenericDraweeHierarchy hierarchy =
                new GenericDraweeHierarchyBuilder(getResources()).setPlaceholderImage(placeholder)
                        .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .build();
        mDraweeHolder = DraweeHolder.create(hierarchy, getContext());
    }

    public void setController(DraweeController controller) {
        mDraweeHolder.setController(controller);
    }

    public void invalidate() {
        invalidated = true;
        super.invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isInEditMode()) {
            createMaskCanvas(w, h, oldw, oldh);
        }
    }

    private void createMaskCanvas(int width, int height, int oldw, int oldh) {
        boolean sizeChanged = width != oldw || height != oldh;
        boolean isValid = width > 0 && height > 0;
        if (isValid && (maskCanvas == null || sizeChanged)) {
            maskCanvas = new Canvas();
            maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            maskCanvas.setBitmap(maskBitmap);

            paintMaskCanvas(maskCanvas, width, height);

            drawableCanvas = new Canvas();
            drawableBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            drawableCanvas.setBitmap(drawableBitmap);
            drawablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            invalidated = true;
        }
    }

    protected void paintMaskCanvas(Canvas maskCanvas, int width, int height) {
        if (shape != null) {
            shape.setBounds(0, 0, width, height);
            shape.draw(maskCanvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount =
                canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        try {
            if (invalidated) {
                setImageDrawable(mDraweeHolder.getTopLevelDrawable());
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    invalidated = false;
                    Matrix imageMatrix = getImageMatrix();
                    if (imageMatrix == null) {// && mPaddingTop == 0 && mPaddingLeft == 0) {
                        drawable.draw(drawableCanvas);
                    } else {
                        int drawableSaveCount = drawableCanvas.getSaveCount();
                        drawableCanvas.save();
                        drawableCanvas.concat(imageMatrix);
                        drawable.draw(drawableCanvas);
                        drawableCanvas.restoreToCount(drawableSaveCount);
                    }

                    drawablePaint.reset();
                    drawablePaint.setFilterBitmap(false);
                    drawablePaint.setXfermode(PORTER_DUFF_XFERMODE);
                    drawableCanvas.drawBitmap(maskBitmap, 0.0f, 0.0f, drawablePaint);
                }
            }

            if (!invalidated) {
                drawablePaint.setXfermode(null);
                canvas.drawBitmap(drawableBitmap, 0.0f, 0.0f, drawablePaint);
            }
        } catch (Exception e) {
            String log = "Exception occured while drawing " + getId();
            Log.e(TAG, log, e);
        } finally {
            canvas.restoreToCount(saveCount);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        attach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        attach();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        detach();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        detach();
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable dr) {
        return dr == mDraweeHolder.getTopLevelDrawable() || super.verifyDrawable(dr);
    }

    private void attach() {
        mDraweeHolder.onAttach();
        mDraweeHolder.getTopLevelDrawable().setCallback(this);
    }

    private void detach() {
        mDraweeHolder.onDetach();
        mDraweeHolder.getTopLevelDrawable().setCallback(null);
    }
}