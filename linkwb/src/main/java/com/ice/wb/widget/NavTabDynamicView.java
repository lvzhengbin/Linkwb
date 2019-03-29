package com.ice.wb.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.ice.common.utils.SystemUtil;
import com.ice.wb.R;

/**
 * 导航动态tab icon切换动效
 * Created by lvzhengbin on 2019/3/24.
 */

public class NavTabDynamicView extends AbsNavTabView {
    private int progress1;
    private float mScale;
    private boolean isScaleRuning = false;

    private ValueAnimator valueAnimator1;

    private Bitmap mUnDynamicTabBitmap;
    private Bitmap mDynamicTabBgBitmap;

    private float mRadius;


    public NavTabDynamicView(Context context) {
        super(context);
        init();
    }

    public NavTabDynamicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavTabDynamicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint.setColor(mMainColor);

        mUnDynamicTabBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lz_main_nav_tab_dynamic_un);
        mDynamicTabBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.moments_anim);
    }

    @Override
    public void setSelect(boolean select){
        /*if(isSelect == select){
            return;
        }*/
        super.setSelect(select);
        if(isSelect) {
            //startAnimator();
            startScaleAnimator();
        }else{
            abortAnimation();
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isSelect){
            if (isScaleRuning){
                drawScaleLiveBgBitmap(canvas);
            }else {
                drawFillAnimator(canvas);
                drawLiveBgBitmap(canvas);
            }
        }else{
            drawUnLiveBitmap(canvas);
        }
    }

    private void drawUnLiveBitmap(Canvas canvas){
        drawBitmapInViewCenter(canvas, mUnDynamicTabBitmap);
    }

    private void drawLiveBgBitmap(Canvas canvas){
        drawBitmapInViewCenter(canvas, mDynamicTabBgBitmap);
    }

    private void drawScaleLiveBgBitmap(Canvas canvas){
        drawScaleBitmapInViewCenter(canvas, mDynamicTabBgBitmap, mScale);
    }

    private void drawFillAnimator(Canvas canvas){
        if (progress1 > 0){
            float cx = getWidth();
            float cy = getHeight()/2 + SystemUtil.dip2px(getContext(), 3);
            mRadius = getWidth()/2 * (progress1/(float)100);
            mPaint.setShader(new LinearGradient(getWidth(), getHeight(), getWidth(), 0, mMainColor, Color.parseColor("#4CFE5353"), Shader.TileMode.CLAMP));
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawCircle(cx, cy, mRadius, mPaint);
        }
    }

    private void abortAnimation(){
        if(valueAnimator1 != null && valueAnimator1.isRunning()){
            valueAnimator1.cancel();
        }
    }

    private void startAnimator() {
        progress1 = 0;
        abortAnimation();
        valueAnimator1 = ValueAnimator.ofInt(0, 100);
        valueAnimator1.setDuration(ANIMATION_FILL_TIME);
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress1 = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator1.start();
    }


    private void startScaleAnimator(){
        mScale = 0.3f;
        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(0.3f, 1f);
        scaleAnimator.setDuration(ANIMATION_SCALE_TIME);
        scaleAnimator.setInterpolator(new LinearInterpolator());
        scaleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isScaleRuning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isScaleRuning = false;
                startAnimator();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScale = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        scaleAnimator.start();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
