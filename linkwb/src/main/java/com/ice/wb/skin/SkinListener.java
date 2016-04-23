package com.ice.wb.skin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.view.LayoutInflater;

/**
 * 换肤接口
 * Created by lvzhengbin on 15/10/23.
 */
public interface SkinListener {

    /**
     * 取消注册广播
     * @param broadcastReceiver
     */
    public void unRegistSkinReceiver(BroadcastReceiver broadcastReceiver);

    /**
     * 注册广播
     * @param broadcastReceiver
     * @param intentFilter
     */
    public void registSkinReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter);

    /**
     * 设置Factory
     * @param factory
     */
    public void setFactory(LayoutInflater.Factory factory);

    /**
     * 更新皮肤
     */
    public void onChangeSkinNotifer();

    public Activity getActivity();
}
