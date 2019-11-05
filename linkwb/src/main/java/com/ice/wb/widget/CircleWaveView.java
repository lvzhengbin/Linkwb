package com.ice.wb.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ice.common.utils.SystemUtil;
import com.ice.wb.R;

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2019/3/24
 */
public class CircleWaveView extends View {

    private static final int ANIMATION_TIME = 550;
    private Paint mPaint;
    private float mRadius;

    private ValueAnimator mValueAnimator;
    private int progress;

    public CircleWaveView(Context context) {
        this(context, null);
    }

    public CircleWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getContext().getResources().getColor(R.color.color_fe5353_10));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int maxRadius = Math.min(cx, cy) + SystemUtil.dip2px(getContext(), 20);
        if (progress * maxRadius > 0){
            mRadius = progress * maxRadius / 100;
            canvas.drawCircle(cx, cy, mRadius, mPaint);
        }
    }

    public void startAnimator() {
        mValueAnimator = ValueAnimator.ofInt(40, 100);
        mValueAnimator.setDuration(ANIMATION_TIME);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mValueAnimator.start();
    }
}
