package com.ice.wb.skin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;

/**
 * Created by lvzhengbin on 15/10/19.
 */
public class SkinResourceUtil {
    private static final String TAG = "SkinResourceUtil";

    public static ColorFilter getSkinColorFilter(Context context){
        int iColor = getSkinColor(context);

        int red = Color.red(iColor);
        int green = Color.green(iColor);
        int blue = Color.blue(iColor);

        // 根据SeekBar定义RGBA的矩阵, 通过修改矩阵第五列颜色的偏移量改变图片的颜色
        float[] src = new float[]{
                1, 0, 0, 0, red,
                0, 1, 0, 0, green,
                0, 0, 1, 0, blue,
                0, 0, 0, 1, 0};
        return new ColorMatrixColorFilter(src);
    }

    private static int getSkinColor(Context context) {
        return SkinSetting.getInstance(context).getThemeSkinColor();
    }

    public static int getSkinTextColor(Context context){
        return SkinSetting.getInstance(context).getThemeSkinColor();
    }
}
