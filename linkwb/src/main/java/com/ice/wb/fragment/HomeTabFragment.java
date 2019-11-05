package com.ice.wb.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ice.common.fragment.BaseLazyFragment;
import com.ice.wb.R;
import com.ice.wb.widget.CircleLoadingView;
import com.ice.wb.widget.CircleWaveView;
import com.ice.wb.widget.NavTabDynamicView;
import com.ice.wb.widget.NavTabHomeView;
import com.ice.wb.widget.NavTabLiveView;
import com.ice.wb.widget.NavTabMineView;

/**
 * Desc:首页tab fragment
 * Created by lvzhengbin
 * Time: 2019/2/23
 */
public class HomeTabFragment extends BaseLazyFragment {

    private static final String TAG = "HomeTabFragment";
    private TextView mTextview;
    private NavTabLiveView mTabLive;
    private NavTabDynamicView mTabDynamic;
    private NavTabMineView mTabMine;
    private NavTabHomeView mTabHome;
    private CircleWaveView mCircleWaveView;
    private CircleLoadingView mCircleLoadingView;

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
        mTextview = (TextView) view.findViewById(R.id.fragment_tag);
        mTextview.setText("首页tab fragment页");
        mTabLive = (NavTabLiveView)view.findViewById(R.id.tab_live);
        mTabDynamic = (NavTabDynamicView) view.findViewById(R.id.tab_dynamic);
        mTabMine = (NavTabMineView)view.findViewById(R.id.tab_mine);
        mTabHome = (NavTabHomeView)view.findViewById(R.id.tab_home);
        mCircleWaveView = (CircleWaveView)view.findViewById(R.id.circle_wave_view);
        mCircleLoadingView = (CircleLoadingView)view.findViewById(R.id.loading_view);
    }

    private void addListener() {
        mTabLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabLive.setSelect(true);
                mCircleWaveView.startAnimator();
                //mCircleLoadingView.startAnimation();
            }
        });

        mTabDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabDynamic.setSelect(true);
            }
        });

        mTabMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabMine.setSelect(true);
            }
        });

        mTabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabHome.setSelect(true);
            }
        });
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
