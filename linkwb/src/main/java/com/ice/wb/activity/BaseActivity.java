package com.ice.wb.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by lvzhengbin on 16/4/18.
 */
public class BaseActivity extends AbsSkinActivity{

    public AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = (AppCompatActivity)getActivity();
    }
}
