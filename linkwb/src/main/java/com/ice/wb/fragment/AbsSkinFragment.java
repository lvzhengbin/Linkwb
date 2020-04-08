package com.ice.wb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;

import com.ice.wb.activity.AbsSkinActivity;


/**
 * Created by lvzhengbin on 15/10/22.
 */
public abstract class AbsSkinFragment extends Fragment {

    private AbsSkinActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = (AbsSkinActivity)activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        return mActivity.getLayoutInflater();
    }

    /**
     * 换肤广播
     */
    protected void sendSkinChangedBroadcast(){
        Intent intent = new Intent();
        intent.setAction("action.skin.changed");
        mActivity.sendBroadcast(intent);
    }

}
