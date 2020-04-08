package com.ice.wb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.wb.R;
import com.ice.wb.model.AppInfo;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lvzhengbin on 16/4/11.
 */
public class TestLocalAppsAdapter extends RecyclerView.Adapter<TestLocalAppsAdapter.MyViewHolder>{

    private Context mContext;

    private List<AppInfo> appInfos;

    public TestLocalAppsAdapter(Context context, List<AppInfo> appInfos) {
        this.mContext = context;
        this.appInfos = appInfos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.test_local_apps_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.appName.setText(appInfos.get(position).getName());
        holder.appIcon.setImageDrawable(appInfos.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.iv_app_icon)
        ImageView appIcon;

        @BindView(R.id.tv_app_name)
        TextView appName;
        public MyViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
            /*appIcon = (ImageView)view.findViewById(R.id.iv_app_icon);
            appName = (TextView) view.findViewById(R.id.tv_app_name);*/
        }
    }

}
