package com.ice.wb.skin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.view.LayoutInflater;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by lvzhengbin on 15/10/24.
 */
public class WeakSkinListener implements SkinListener {

    private Reference<SkinListener> mReference;

    public WeakSkinListener(SkinListener skinListener) {
        mReference = new WeakReference<SkinListener>(skinListener);
    }

    @Override
    public void unRegistSkinReceiver(BroadcastReceiver broadcastReceiver) {
        if (mReference != null){
            mReference.get().unRegistSkinReceiver(broadcastReceiver);
        }
    }

    @Override
    public void registSkinReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        if (mReference != null){
            mReference.get().registSkinReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    public void setFactory(LayoutInflater.Factory factory) {
        if (mReference != null){
            mReference.get().setFactory(factory);
        }
    }

    @Override
    public void onChangeSkinNotifer() {
        if(mReference != null){
            mReference.get().onChangeSkinNotifer();
        }
    }

    @Override
    public Activity getActivity() {
        if(mReference != null){
            return mReference.get().getActivity();
        }
        return null;
    }
}
