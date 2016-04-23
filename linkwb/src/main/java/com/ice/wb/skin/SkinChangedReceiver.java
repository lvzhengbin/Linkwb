package com.ice.wb.skin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 换肤广播
 * Created by lvzhengbin on 15/10/23.
 */
public class SkinChangedReceiver extends BroadcastReceiver{

    private SkinEngine mSkinEngine;

    public SkinChangedReceiver(SkinEngine skinEngine) {
        this.mSkinEngine = skinEngine;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("action.skin.changed")){
            //todo 全局控件换肤
            mSkinEngine.onChangedSkinNotifer();
        }
    }
}
