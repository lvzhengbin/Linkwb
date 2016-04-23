package com.ice.wb.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ice.wb.R;
import com.ice.wb.fragment.PlanetFragment;
import com.ice.wb.fragment.TestRxFragment;
import com.ice.wb.skin.SkinSetting;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNagigationView;
    /** 菜单打开/关闭状态 */
    private boolean isDirection_left = false;

    private Fragment contentFragment;
    private int currentDrawerItem = 0;

    //@Bind(R.id.toolbar)
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        setContentView(R.layout.wb_main_activity);
        mPlanetTitles = getResources().getStringArray(R.array.menu_array);
        initView();
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
        addListeners();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNagigationView = (NavigationView)findViewById(R.id.id_navigationview);
        mNagigationView.inflateHeaderView(R.layout.wb_header_nav);
        mNagigationView.inflateMenu(R.menu.menu_nav);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        ActionBarActivity toolbarActivity = (ActionBarActivity) getActivity();
        toolbarActivity.setSupportActionBar(mToolbar);
        toolbarActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("首页");

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        initFragment();
    }

    private void initFragment() {
        if (contentFragment == null){
            contentFragment = new TestRxFragment();
        }
        Bundle args = new Bundle();
        args.putString("key", mPlanetTitles[0]);
        contentFragment.setArguments(args);

        // 通过替换已存在的fragment来插入新的fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.drawer_content, contentFragment)
                .commit();
    }

    private void addListeners() {
        mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());
        mNagigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_menu_setting){
                    selectItem(0);
                }else if (itemId == R.id.nav_menu_categories){
                    selectItem(1);
                }else if (itemId == R.id.nav_menu_feedback){
                    selectItem(2);
                }else if (itemId == R.id.nav_menu_loginOut){
                    selectItem(3);
                }
                return false;
            }
        });
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

    private void selectItem(int position) {
        if (position == currentDrawerItem){
            mDrawerLayout.closeDrawer(mNagigationView);
            return;
        }
        currentDrawerItem = position;
        // 创建一个新的fragment并且根据行星的位置来显示
        contentFragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putString("key", mPlanetTitles[position]);
        contentFragment.setArguments(args);

        // 通过替换已存在的fragment来插入新的fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.drawer_content, contentFragment)
                .commit();
        //高亮被选择的item, 更新标题, 并关闭drawer
        mToolbar.setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mNagigationView);
    }

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
