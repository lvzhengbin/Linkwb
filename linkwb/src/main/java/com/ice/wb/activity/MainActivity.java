package com.ice.wb.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ice.wb.R;
import com.ice.wb.delegate.MainBottomTabBarDelegate;
import com.ice.wb.fragment.DynamicTabFragment;
import com.ice.wb.fragment.HomeTabFragmentKt;
import com.ice.wb.fragment.LiveTabFragment;
import com.ice.wb.fragment.MessageTabFragment;
import com.ice.wb.fragment.MyTabFragment;
import com.ice.wb.skin.SkinSetting;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import androidx.appcompat.app.ActionBarActivity;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNagigationView;
    /** 菜单打开/关闭状态 */
    private boolean isDirection_left = false;

    public static final int TAB_HOME = 0;
    public static final int TAB_LIVE = 1;
    public static final int TAB_DYNAMIC = 2;
    public static final int TAB_MESSAGE = 3;
    public static final int TAB_MY = 4;
    private final static int HOME_PAGES = 5;
    private Fragment[] mFragments;
    private Fragment mCurrentFragment;//当前正显示的fragment


    private int currentDrawerItem = 0;

    //@Bind(R.id.toolbar)
    private Toolbar mToolbar;
    private MainBottomTabBarDelegate mBottomTabBarDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar
                mDrawerLayout.setFitsSystemWindows(true);
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                mDrawerLayout.setClipToPadding(false);
            }
        }
        setContentView(R.layout.wb_main_activity);
        mPlanetTitles = getResources().getStringArray(R.array.menu_array);
        initView();
        addListeners();
        //默认显示首页tab
        if (mBottomTabBarDelegate != null){
            mBottomTabBarDelegate.setTabSelected(TAB_HOME);
        }
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNagigationView = (NavigationView)findViewById(R.id.id_navigationview);
        mNagigationView.inflateHeaderView(R.layout.wb_header_nav);
        mNagigationView.inflateMenu(R.menu.menu_nav);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        AppCompatActivity toolbarActivity = (AppCompatActivity) getActivity();
        toolbarActivity.setSupportActionBar(mToolbar);
        toolbarActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("首页");

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mBottomTabBarDelegate = new MainBottomTabBarDelegate(this, findViewById(R.id.ktv_homepage_bottomBar));
        initFragment();
    }

    private void initFragment() {
        mFragments = new Fragment[HOME_PAGES];
        for (int i = 0; i < HOME_PAGES; i++) {
            mFragments[i] = getFragment(i);
        }
        loadMultipleRootTransaction(getSupportFragmentManager(), R.id.drawer_content, mFragments, TAB_HOME);

        /*if (mCurrentFragment == null){
            mCurrentFragment = new TestRxFragment();
        }
        Bundle args = new Bundle();
        args.putString("key", mPlanetTitles[0]);
        mCurrentFragment.setArguments(args);

        // 通过替换已存在的fragment来插入新的fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.drawer_content, mCurrentFragment)
                .commit();*/
    }


    private Fragment getFragment(int index){
        Fragment fragment;
        switch (index){
            case TAB_HOME:
                fragment = new HomeTabFragmentKt();
                break;
            case TAB_LIVE:
                fragment = new LiveTabFragment();
                break;
            case TAB_DYNAMIC:
                fragment = new DynamicTabFragment();
                break;
            case TAB_MESSAGE:
                fragment = new MessageTabFragment();
                break;
            case TAB_MY:
                fragment = new MyTabFragment();
                break;
            default:
                fragment = new HomeTabFragmentKt();
                break;
        }
        return fragment;
    }

    private void addListeners() {
        mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());
        mNagigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                /*int itemId = item.getItemId();
                if (itemId == R.id.nav_menu_setting){
                    selectItem(0);
                }else if (itemId == R.id.nav_menu_categories){
                    selectItem(1);
                }else if (itemId == R.id.nav_menu_feedback){
                    selectItem(2);
                }else if (itemId == R.id.nav_menu_loginOut){
                    selectItem(3);
                }*/
                return false;
            }
        });
        mBottomTabBarDelegate.setOnTabChangedListener(new MainBottomTabBarDelegate.OnTabChangedListener() {
            @Override
            public void onTabChanged(int curTabPosition, int preTabPosition) {
                Log.d(TAG, "curTabPosition = " + curTabPosition + ", preTabPosition = " + preTabPosition);
                onFragmentTabChanged(curTabPosition, preTabPosition);
            }
        });
    }

    private void loadMultipleRootTransaction(FragmentManager fragmentManager, int containerId, Fragment[] tos, int defaultTabIndex) {
        if (fragmentManager == null || tos.length <= 0) {
            return;
        }
        int index = 0;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (Fragment to : tos) {
            if (to != null) {
                String toName = to.getClass().getName();
                if (to.isAdded()) {
                    //Fragment重复add会抛异常
                    ft.attach(to);
                } else {
                    ft.add(containerId, to, toName);
                }
                if (index == defaultTabIndex){
                    to.setUserVisibleHint(true);
                    ft.show(to);
                }else {
                    to.setUserVisibleHint(false);
                    ft.hide(to);
                }
                index++;
            }
        }
        ft.commitAllowingStateLoss();
    }

    private void onFragmentTabChanged(int tabIndex, int preTabIndex) {
        if (mFragments != null && mFragments.length > tabIndex) {
            Fragment fragment = mFragments[tabIndex];
            if (fragment == null || !fragment.isAdded()) {
                Log.d(TAG, fragment == null ? "fragment is null" : "fragment isAdded() " + fragment.isAdded());
                return;
            }
        }

        if (tabIndex != preTabIndex && mFragments != null && mFragments.length > 0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (preTabIndex >= 0 && mFragments.length > preTabIndex) {
                Fragment preFragment = mFragments[preTabIndex];
                if (preFragment.isAdded()) {
                    Log.d(TAG, "hide(preFragment) tabIndex = " + preTabIndex);
                    ft.hide(preFragment);
                    preFragment.setUserVisibleHint(false);
                }
            }
            if (tabIndex >= 0) {
                if (mFragments.length > tabIndex) {
                    /*if (mCurrentFragment != null && mCurrentFragment.isAdded()) {
                        ft.hide(mCurrentFragment);
                        mCurrentFragment.setUserVisibleHint(false);
                    }*/
                    mCurrentFragment = mFragments[tabIndex];
                    if (mCurrentFragment.isAdded()) {
                        Log.d(TAG, "show(mCurrentFragment) tabIndex = " + tabIndex);
                        ft.show(mCurrentFragment);
                        mCurrentFragment.setUserVisibleHint(true);
                    }
                }
            }
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * DrawerLayout状态变化监听
     */
    private class DrawerLayoutStateListener extends DrawerLayout.SimpleDrawerListener{
        /**
         * 当导航菜单滑动的时候被执行
         */
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
        }

        /**
         * 当导航菜单打开时执行
         */
        @Override
        public void onDrawerOpened(View drawerView) {
            isDirection_left = true;
        }

        /**
         * 当导航菜单关闭时执行
         */
        @Override
        public void onDrawerClosed(View drawerView) {
            isDirection_left = false;
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
        }
    }

    /*private void selectItem(int position) {
        if (position == currentDrawerItem){
            mDrawerLayout.closeDrawer(mNagigationView);
            return;
        }
        currentDrawerItem = position;
        // 创建一个新的fragment并且根据行星的位置来显示
        mCurrentFragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putString("key", mPlanetTitles[position]);
        mCurrentFragment.setArguments(args);

        // 通过替换已存在的fragment来插入新的fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.drawer_content, mCurrentFragment)
                .commit();
        //高亮被选择的item, 更新标题, 并关闭drawer
        mToolbar.setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mNagigationView);
    }*/

    /**
     * 点击ActionBar上菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            if (!isDirection_left) { // 左边栏菜单关闭时，打开
                mDrawerLayout.openDrawer(mNagigationView);
            } else {// 左边栏菜单打开时，关闭
                mDrawerLayout.closeDrawer(mNagigationView);
            }
        }else if(id == R.id.action_skin_settings){
            Toast.makeText(mContext, "随机换肤", Toast.LENGTH_LONG).show();
            SkinSetting.getInstance(mContext).setRandomColor();
            sendSkinChangedBroadcast();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 加载菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
