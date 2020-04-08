package com.ice.wb.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ice.common.utils.SystemUtil;
import com.ice.wb.R;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2019/3/26
 */
public class CircleLoadingView extends View {

    private Paint paint;
    private float degrees = 0f;
    private float mMaxRadius = SystemUtil.dip2px(getContext(),15);

    public CircleLoadingView(Context context) {
        super(context);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int strokeWidth = SystemUtil.dip2px(getContext(), 3);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(getContext().getResources().getColor(R.color.color_fe5353_20));
        float x = (getWidth()) / (float)2;
        float y = (getHeight()) / (float)2;
        float radius = Math.min(x, y) > mMaxRadius ? mMaxRadius : Math.min(x, y);
        canvas.translate(x, y);
        canvas.rotate(degrees);
        float halfStrokeWidth = strokeWidth / (float) 2;
        canvas.drawCircle(0, 0, radius - halfStrokeWidth, paint);

        RectF rectF = new RectF(0-radius+halfStrokeWidth,0-radius+halfStrokeWidth, radius-halfStrokeWidth, radius-halfStrokeWidth);
        Paint arcPaint = new Paint();
        arcPaint.setColor(getContext().getResources().getColor(R.color.color_fe5353));
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidth);
        arcPaint.setAntiAlias(true);
        canvas.drawArc(rectF, -90, 180, false, arcPaint);
    }

    public void startAnimation(){
        ValueAnimator rotateAnim = ValueAnimator.ofFloat(0, 360);
        rotateAnim.setDuration(650);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (float) animation.getAnimatedValue();
                Log.d("CircleLoadingView", "onAnimationUpdate degrees = " + degrees);
                postInvalidate();
            }
        });
        rotateAnim.start();
    }


}
