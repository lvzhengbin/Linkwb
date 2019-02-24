package com.ice.wb.delegate;

import android.content.Context;
import android.view.View;

import com.ice.wb.R;
import com.ice.wb.widget.TabButtonView;

import java.util.ArrayList;

/**
 * Desc:底部tab导航条delegate
 * Created by lvzhengbin
 * Time: 2019/2/23
 */
public class MainBottomTabBarDelegate implements View.OnClickListener {

    private Context mContext;
    private TabButtonView mHomeTabBtn;
    private TabButtonView mLiveTabBtn;
    private TabButtonView mDynamicTabBtn;
    private TabButtonView mMessageTabBtn;
    private TabButtonView mMyTabBtn;

    private int mCurrentTabIndex = -1;
    private ArrayList<TabButtonView> mBottomTabs = new ArrayList<>();
    private OnTabChangedListener mTabChangedListener;


    public MainBottomTabBarDelegate(Context context, View parentView) {
        this.mContext = context;
        initView(parentView);
        addListener();
    }

    private void initView(View view) {
        mHomeTabBtn = (TabButtonView)view.findViewById(R.id.tab_btn_homepage);
        mLiveTabBtn = (TabButtonView)view.findViewById(R.id.tab_btn_live);
        mDynamicTabBtn = (TabButtonView)view.findViewById(R.id.tab_btn_focus);
        mMessageTabBtn = (TabButtonView)view.findViewById(R.id.tab_btn_message);
        mMyTabBtn = (TabButtonView)view.findViewById(R.id.tab_btn_my);
        mBottomTabs.add(mHomeTabBtn);
        mBottomTabs.add(mLiveTabBtn);
        mBottomTabs.add(mDynamicTabBtn);
        mBottomTabs.add(mMessageTabBtn);
        mBottomTabs.add(mMyTabBtn);

    }

    private void addListener() {
        mHomeTabBtn.setOnClickListener(this);
        mLiveTabBtn.setOnClickListener(this);
        mDynamicTabBtn.setOnClickListener(this);
        mMessageTabBtn.setOnClickListener(this);
        mMyTabBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tab_btn_homepage){
            setTabSelected(0);
        }else if (id == R.id.tab_btn_live){
            setTabSelected(1);
        }else if (id == R.id.tab_btn_focus){
            setTabSelected(2);
        }else if (id == R.id.tab_btn_message){
            setTabSelected(3);
        }else if (id == R.id.tab_btn_my){
            setTabSelected(4);
        }
    }

    public void setTabSelected(int position){
        if (mCurrentTabIndex >= 0 && mCurrentTabIndex < mBottomTabs.size() && mCurrentTabIndex != position){
            mBottomTabs.get(mCurrentTabIndex).setSelected(false);
            mBottomTabs.get(position).setSelected(true);
        }else {
            mBottomTabs.get(position).setSelected(true);
        }
        if (mTabChangedListener != null){
            mTabChangedListener.onTabChanged(position, mCurrentTabIndex);
        }
        mCurrentTabIndex = position;
    }

    public void setOnTabChangedListener(OnTabChangedListener onTabChangedListener){
        this.mTabChangedListener = onTabChangedListener;
    }

    public interface OnTabChangedListener{
        void onTabChanged(int curTabPosition, int preTabPosition);
    }
}
