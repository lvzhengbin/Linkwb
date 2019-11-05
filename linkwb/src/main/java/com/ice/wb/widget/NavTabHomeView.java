package com.ice.wb.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;

import com.ice.common.utils.SystemUtil;
import com.ice.wb.R;

/**
 * 导航首页tab icon切换动效
 * Created by lvzhengbin on 2019/3/25.
 */

public class NavTabHomeView extends AbsNavTabView {
    private int progress1;
    private float mScale;
    private boolean isScaleRuning = false;

    private ValueAnimator valueAnimator1;

    private Bitmap mUnTabBitmap;
    private Bitmap mTabBitmap;

    private RectF mRectfill;
    private Path mPath;


    public NavTabHomeView(Context context) {
        super(context);
        init();
    }

    public NavTabHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavTabHomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mRectfill = new RectF();
        mPath = new Path();
        mPaint.setColor(mMainColor);
        PathEffect pathEffect = new CornerPathEffect(4);
        mPaint.setPathEffect(pathEffect);

        mUnTabBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lz_main_nav_tab_home_un);
        mTabBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home_anim);
    }

    @Override
    public void setSelect(boolean select){
        /*if(isSelect == select){
            return;
        }*/
        super.setSelect(select);
        if(isSelect) {
            startAnimator();
            //startScaleAnimator();
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
                drawScaleTabBitmap(canvas);
            }else {
                drawFillAnimator(canvas);
                drawTabBitmap(canvas);
            }
        }else{
            drawUnTabBitmap(canvas);
        }
    }

    private void drawUnTabBitmap(Canvas canvas){
        drawBitmapInViewCenter(canvas, mUnTabBitmap);
    }

    private void drawTabBitmap(Canvas canvas){
        drawBitmapInViewCenter(canvas, mTabBitmap);
    }

    private void drawScaleTabBitmap(Canvas canvas){
        drawScaleBitmapInViewCenter(canvas, mTabBitmap, mScale);
    }

    private void drawFillAnimator(Canvas canvas){
        if (progress1 > 0){
            float filX = (float) getWidth() - (getWidth() / 2) * progress1 / (float) 100;
            float fillY = (float) getHeight() - getHeight() * (progress1 / (float) 100);
            mRectfill.left = filX;
            mRectfill.top = (float)getHeight() - (getHeight() * 7/(float)10) * (progress1/(float)100);
            mRectfill.right = getWidth();
            mRectfill.bottom = getHeight();
            Log.d("lvzb", "mRectfill.left = " + filX+ ", fillY = " + fillY + ", progress1 = " + progress1);
            int rx = SystemUtil.dip2px(getContext(), 2);

            float x0 = (mRectfill.right + mRectfill.left) / 2;
            float y0 = mRectfill.bottom;
            float y1 = mRectfill.top;

            mPaint.setShader(new LinearGradient(x0,y0, x0, y1, mMainColor, Color.parseColor("#4CFE5353"), Shader.TileMode.CLAMP));
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            mPaint.setStrokeCap(Paint.Cap.ROUND);

            mPath.reset();
            mPath.moveTo(getWidth(), getHeight());
            mPath.lineTo(filX, getHeight());
            mPath.rLineTo(0, -(getHeight() -filX));
            mPath.lineTo(getWidth(), fillY);

            canvas.drawPath(mPath, mPaint);
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
        valueAnimator1.setInterpolator(new AnticipateOvershootInterpolator());
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
