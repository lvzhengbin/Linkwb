package com.ice.wb.skin;

import android.content.Context;
import android.content.IntentFilter;

/**
 * 皮肤引擎
 * Created by lvzhengbin on 15/10/23.
 */
public class SkinEngine {

    private SkinChangedReceiver mSkinBroadcastReceiver;

    private WeakSkinListener mWeakSkinListener;

    private SkinFactory mFactory;

    public SkinEngine(WeakSkinListener mWeakSkinListener) {
        this.mWeakSkinListener = mWeakSkinListener;
    }

    /**
     * 设置引擎数据
     */
    public void setUpData(){
        if (mWeakSkinListener != null){
            mFactory = new SkinFactory();
            mWeakSkinListener.setFactory(mFactory);

            IntentFilter filter = new IntentFilter();
            filter.addAction("action.skin.changed");
            mSkinBroadcastReceiver = new SkinChangedReceiver(this);
            mWeakSkinListener.registSkinReceiver(mSkinBroadcastReceiver, filter);
        }
    }

    public void onChangedSkinNotifer(){
        if (mWeakSkinListener != null){
            if(mFactory != null){
                mFactory.onSkinChanged(getContext());
            }
        }
    }

    private Context getContext(){
        if (mWeakSkinListener != null){
            return mWeakSkinListener.getActivity().getApplicationContext();
        }
        return null;
    }

    public void unRegisterSkinBrocastReceiver(){
        if (mWeakSkinListener != null){
            mWeakSkinListener.unRegistSkinReceiver(mSkinBroadcastReceiver);
        }
    }

}
