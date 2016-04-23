package com.ice.wb.model;

import android.graphics.drawable.Drawable;

/**
 * Created by lvzhengbin on 16/4/6.
 */
public class AppInfo implements Comparable{

    long lastUpdateTime;
    String name;
    Drawable icon;

    public AppInfo() {
    }

    public AppInfo(String nName, long lastUpdateTime, Drawable icon) {
        this.name = nName;
        this.icon = icon;
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo)another;
        return getName().compareTo(f.getName());
    }
}
