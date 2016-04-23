package com.ice.wb.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by lvzhengbin on 16/4/18.
 */
public class BaseActivity extends AbsSkinActivity{

    public ActionBarActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = (ActionBarActivity)getActivity();
    }
}
