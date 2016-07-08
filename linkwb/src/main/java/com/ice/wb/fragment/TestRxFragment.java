package com.ice.wb.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ice.wb.R;
import com.ice.wb.adapter.DividerItemDecoration;
import com.ice.wb.adapter.TestLocalAppsQuickAdapter;
import com.ice.wb.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lvzhengbin on 15/10/19.
 */
public class TestRxFragment extends AbsSkinFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "TestRxFragment";

    private Context mContext;

    @Bind(R.id.app_list_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.app_list_recyclerView)
    RecyclerView mRecyclerView;

    //private TestLocalAppsAdapter adapter;
    private TestLocalAppsQuickAdapter adapter;

    private Subscription mSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_local_apps_list_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        this.mContext = getContext();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, false));

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        addListener();
    }

    private void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshTheList();
    }

    @Override
    public void onRefresh() {
        refreshTheList();
    }

    private Observable<List<AppInfo>> getApps(){
        return Observable.create(new Observable.OnSubscribe<List<AppInfo>>() {
            @Override
            public void call(Subscriber<? super List<AppInfo>> subscriber) {
                Log.d(TAG, "getApps() Observable create");
                List<AppInfo> apps = new ArrayList<AppInfo>();
                final Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

                PackageManager packageManager = getActivity().getPackageManager();
                List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
                for (PackageInfo info : installedPackages){
                    AppInfo appInfo = new AppInfo();
                    appInfo.setIcon(info.applicationInfo.loadIcon(packageManager));
                    appInfo.setName(info.applicationInfo.loadLabel(packageManager).toString());

                    apps.add(appInfo);

                    if (subscriber.isUnsubscribed()){//是否已取消订阅关系
                        return;
                    }
                }
                subscriber.onNext(apps);
                subscriber.onCompleted();
            }
        });
    }

    private void refreshTheList(){
        mSwipeRefreshLayout.setRefreshing(true);
        mSubscription = getApps()
                //.toSortedList()//toSortedList()方法将发射的数据转换成为一个List<appinfo>
                .subscribeOn(Schedulers.newThread())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                        Toast.makeText(getActivity(), "Here is the list!", Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError()");
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        Log.d(TAG, "onNext() appInfos is size " + appInfos.size());
                        Toast.makeText(getActivity(), "Here is the onNext(): " + appInfos.size(), Toast.LENGTH_LONG).show();
                        adapter = new TestLocalAppsQuickAdapter(appInfos);
                        initHeadView();
                        initFooter();
                        mRecyclerView.setAdapter(adapter);
                    }
                });
    }


    private void initHeadView(){
        View headView = LayoutInflater.from(mContext).inflate(R.layout.test_recycleview_head, null);
        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(mContext, 80)));
        adapter.addHeaderView(headView);
    }

    private void initFooter(){
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.test_recycleview_footer, null);
        footerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(mContext, 80)));
        adapter.addFooterView(footerView);
    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }
}
