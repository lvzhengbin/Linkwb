package com.ice.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2019/3/4
 */
public abstract class BaseLazyFragment extends Fragment {


    protected boolean isLazyLoaded;//Fragment懒加载过
    protected boolean isPrepared;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad(); //只有Fragment onCreateView好了，调用一次lazyLoad()
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLazyLoaded = false;
        isPrepared = false;
    }

    @UiThread
    public void onLazyLoad() {}
}
