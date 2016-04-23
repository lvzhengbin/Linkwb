package com.ice.wb.skin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.wb.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by lvzhengbin on 15/10/19.
 */
public class SkinFactory implements LayoutInflater.Factory{

    private static final String TAG = "SkinFactory";

    private ArrayList<WeakReference<View>> skinViewBgList = new ArrayList<WeakReference<View>>();

    private ArrayList<WeakReference<View>> skinViewSrcList = new ArrayList<WeakReference<View>>();

    private ArrayList<WeakReference<TextView>> themeColorTextList = new ArrayList<WeakReference<TextView>>();


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int i = name.indexOf(".");
            if (i > 0) {
                // 自定义控件
                view = inflater.createView(name, null, attrs);
            }else{
                view = inflater.createView(name, "android.widget.", attrs);
            }

            if (view != null) {
                setAttribute(context, view, attrs);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void setAttribute(Context context, View view, AttributeSet attrs) {
        int attributeCount = attrs.getAttributeCount();
        if (attributeCount > 0){
            for (int i = 0; i < attributeCount; i++){
                String name = attrs.getAttributeName(i);
                String value = attrs.getAttributeValue(i);

                if("contentDescription".equals(name)){
                    Log.d(TAG, "contentDescription value = " + value);
                    if(value.startsWith("@" + R.string.skin_bg_color_changed_key)){
                        setViewBgSkinColorFilter(context, view);
                        skinViewBgList.add(new WeakReference<View>(view));

                    }else if(value.startsWith("@" + R.string.skin_icon_color_changed_key)){
                        setViewSrcColorFilter(context, view);
                        skinViewSrcList.add(new WeakReference<View>(view));
                    }

                }else if("textColor".equals(name)){
                    // 更换字体颜色
                    if(view instanceof TextView){
                        if(value.startsWith("@" + R.color.theme_text_color)){
                            ((TextView) view).setTextColor(SkinResourceUtil.getSkinTextColor(context));
                            themeColorTextList.add(new WeakReference<TextView>((TextView)view));
                        }
                    }
                }
            }
        }
    }

    private void setViewSrcColorFilter(Context context, View view) {
        if(view instanceof ImageView){
            ((ImageView) view).setColorFilter(SkinResourceUtil.getSkinColorFilter(context));
        }
    }

    private void setViewBgSkinColorFilter(Context context, View view) {
        Drawable background = view.getBackground();
        if(background != null){
            background.setColorFilter(SkinResourceUtil.getSkinColorFilter(context));
        }
    }

    public void onSkinChanged(Context context){
        for (WeakReference<View> item : skinViewBgList){
            View view = item.get();
            if(view != null){
                setViewBgSkinColorFilter(context, view);
            }else{
                skinViewBgList.remove(item);
            }
        }

        for (WeakReference<View> item : skinViewSrcList){
            View view = item.get();
            if(view != null){
                setViewSrcColorFilter(context, view);
            }else {
                skinViewSrcList.remove(item);
            }
        }

        for (WeakReference<TextView> item : themeColorTextList){
            TextView textView = item.get();
            if(textView != null){
                textView.setTextColor(SkinResourceUtil.getSkinTextColor(context));
            }else{
                themeColorTextList.remove(item);
            }
        }
    }

}
