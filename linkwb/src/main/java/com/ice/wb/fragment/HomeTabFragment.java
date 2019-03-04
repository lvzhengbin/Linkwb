package com.ice.wb.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ice.common.fragment.BaseLazyFragment;
import com.ice.wb.R;

/**
 * Desc:首页tab fragment
 * Created by lvzhengbin
 * Time: 2019/2/23
 */
public class HomeTabFragment extends BaseLazyFragment {

    private static final String TAG = "HomeTabFragment";
    private TextView mTextview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_planet, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated 首页tab fragment");
        mTextview = (TextView) view.findViewById(R.id.fragment_tag);
        mTextview.setText("首页tab fragment页");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint() 首页tab isVisibleToUser = " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onLazyLoad() {
        super.onLazyLoad();
        Log.d(TAG, "onLazyLoad() 首页tab fragment");
        mTextview.setText("首页tab fragment页 onLazyLoad()");
    }
}
