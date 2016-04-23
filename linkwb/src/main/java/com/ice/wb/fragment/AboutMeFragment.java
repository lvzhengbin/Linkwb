package com.ice.wb.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ice.wb.R;

/**
 * Created by lvzhengbin on 15/10/19.
 */
public class AboutMeFragment extends AbsSkinFragment{

    private static final String TAG = "AboutMeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_planet, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.fragment_tag);
        String tag = this.getArguments().getString("key");
        tv.setText("这是选择" + tag + "的内容");
    }
}
