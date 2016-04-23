package com.ice.wb.skin;

import android.content.Context;

import com.ice.wb.R;

import java.util.Random;

/**
 * Created by lvzhengbin on 15/10/22.
 */
public class SkinSetting {

    private static Context mContext;

    private static SkinSetting skinSetting;

    private SkinSetting(Context context){
        this.mContext = context;
    }

    public synchronized static SkinSetting getInstance(Context context){
        if(skinSetting == null){
            skinSetting = new SkinSetting(context);
        }
        return skinSetting;
    }

    private int currentSkinColor = 0;

    private int[] skinColor = new int[]{
            R.color.theme_skin_color_1,
            R.color.theme_skin_color_2,
            R.color.theme_skin_color_3,
            R.color.theme_skin_color_4,
            R.color.theme_skin_color_5,
            R.color.theme_skin_color_6,
            R.color.theme_skin_color_7
    };

    public void setRandomColor(){
        Random random = new Random();
        int nextInt = random.nextInt(skinColor.length);
        currentSkinColor = mContext.getResources().getColor(skinColor[nextInt]);

    }

    public int getThemeSkinColor(){
        if(currentSkinColor == 0){
            currentSkinColor = mContext.getResources().getColor(R.color.theme_default_color);
        }
        return currentSkinColor;
    }
}
