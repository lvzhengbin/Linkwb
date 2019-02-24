package com.ice.wb.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ice.wb.R;

/**
 * Desc:动态tab fragment
 * Created by lvzhengbin
 * Time: 2019/2/23
 */
public class DynamicTabFragment extends AbsSkinFragment{

    private static final String TAG = "DynamicTabFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_planet, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated 动态tab fragment");
        TextView tv = (TextView) view.findViewById(R.id.fragment_tag);
        tv.setText("动态tab fragment页");
    }
}
