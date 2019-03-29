package com.ice.wb.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ice.common.utils.SystemUtil;

/**
 * Created by lvzhengbin on 2019/3/24.
 */

public abstract class AbsNavTabView extends View{
    /**填充动效时长*/
    protected final int ANIMATION_FILL_TIME = 300;
    /**缩放动效时长*/
    protected final int ANIMATION_SCALE_TIME = 100;

    protected boolean isSelect;

    protected float progress;
    protected int mMainColor;

    protected RectF mRectf;
    protected Paint mPaint;

    protected float dip;

    public AbsNavTabView(Context context) {
        super(context);
        initData();
    }

    public AbsNavTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public AbsNavTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData(){
        isSelect = false;
        dip = SystemUtil.getDensity(getContext());
        mMainColor = Color.parseColor("#FE5353");

        mRectf = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setSelect(boolean select){
        isSelect = select;
    }

    /**
     * 绘制正常大小的Bitmap
     * @param canvas
     * @param bitmap
     */
    protected void drawBitmapInViewCenter(Canvas canvas, Bitmap bitmap){
        drawScaleBitmapInViewCenter(canvas, bitmap, 1f);
    }

    /**
     * 绘制缩放的Bitmap
     * @param canvas
     * @param bitmap
     * @param scale 缩放比 0～1
     */
    protected void drawScaleBitmapInViewCenter(Canvas canvas, Bitmap bitmap, float scale){
        float bitmapWidth = bitmap.getWidth() * scale;
        float bitmapHeight = bitmap.getHeight() * scale;
        float startX = (getWidth() - bitmapWidth)/2;
        float startY = (getHeight() - bitmapHeight)/2;
        mRectf.set(startX,startY,startX + bitmapWidth,startY + bitmapHeight);
        canvas.drawBitmap(bitmap,null,mRectf, mPaint);
    }

    public boolean isSelect(){
        return isSelect;
    }

}
