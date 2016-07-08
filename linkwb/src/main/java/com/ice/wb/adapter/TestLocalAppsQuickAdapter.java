package com.ice.wb.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.ice.common.adapter.base.BaseQuickAdapter;
import com.ice.common.adapter.base.BaseViewHolder;
import com.ice.wb.R;
import com.ice.wb.model.AppInfo;

import java.util.List;

/**
 * Created by lvzhengbin on 16/7/7.
 */
public class TestLocalAppsQuickAdapter extends BaseQuickAdapter<AppInfo> {

    public TestLocalAppsQuickAdapter(List<AppInfo> data) {
        super(R.layout.test_local_apps_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
        ImageView appIcon = helper.getView(R.id.iv_app_icon);
        TextView appName = helper.getView(R.id.tv_app_name);
        appName.setText(item.getName());
        appIcon.setImageDrawable(item.getIcon());
    }
}
