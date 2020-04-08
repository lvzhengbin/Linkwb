package com.ice.wb.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ice.common.fragment.BaseLazyFragment;
import com.ice.wb.R;
import com.ice.wb.widget.SquareProgressBar;

/**
 * Desc:首页tab fragment
 * Created by lvzhengbin
 * Time: 2019/2/23
 */
public class HomeTabFragment extends BaseLazyFragment {

    private static final String TAG = "HomeTabFragment";
    private TextView mTextview;
    private SquareProgressBar mSquareProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wb_tab_home_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated 首页tab fragment");
        initView(view);
        addListener();
    }

    private void initView(View view) {
        mTextview = view.findViewById(R.id.fragment_tag);
        mTextview.setText("首页tab fragment页");
        mSquareProgressBar = view.findViewById(R.id.wb_square_progress);
        mSquareProgressBar.setImage(R.drawable.station_post_default_bg);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(4000);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            int animatedValue = (int) valueAnimator1.getAnimatedValue();
            mSquareProgressBar.setProgress(animatedValue);
        });
        valueAnimator.start();
    }

    private void addListener() {

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
