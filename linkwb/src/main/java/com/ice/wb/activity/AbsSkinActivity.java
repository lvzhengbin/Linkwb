package com.ice.wb.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.ice.wb.skin.SkinEngine;
import com.ice.wb.skin.SkinListener;
import com.ice.wb.skin.WeakSkinListener;

import androidx.appcompat.app.AppCompatActivity;

//import androidx.appcompat.app.ActionBarActivity;


/**
 * Created by lvzhengbin on 15/10/22.
 */
public class AbsSkinActivity extends AppCompatActivity implements SkinListener {

    public Context mContext;
    private SkinEngine mSkinEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        WeakSkinListener listener = new WeakSkinListener(this);
        mSkinEngine = new SkinEngine(listener);
        mSkinEngine.setUpData();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSkinEngine != null) {
            mSkinEngine.unRegisterSkinBrocastReceiver();
        }
    }

    @Override
    public void unRegistSkinReceiver(BroadcastReceiver broadcastReceiver) {
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void registSkinReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void setFactory(LayoutInflater.Factory factory) {
        if (getLayoutInflater().getFactory() == null) {
            getLayoutInflater().setFactory(factory);
        }
    }

    @Override
    public void onChangeSkinNotifer() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 换肤广播
     */
    protected void sendSkinChangedBroadcast(){
        Intent intent = new Intent();
        intent.setAction("action.skin.changed");
        sendBroadcast(intent);
    }
}
