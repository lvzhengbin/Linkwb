package com.ice.common.utils;

import android.os.Looper;
import android.util.Log;

/**
 * Desc:单例
 * Created by lvzhengbin
 * Time: 2019/11/6
 */
public class PollingTaskManager {

    private static final PollingTaskManager ourInstance = new PollingTaskManager();
    private static PollingTask mPollingTask;

    public static PollingTaskManager getInstance() {
        return ourInstance;
    }

    private PollingTaskManager() {
        mPollingTask = new PollingTask();
    }

    public void startTask(int heartBeatRate){
        mPollingTask.createTask(heartBeatRate)
                .setOnTaskListener(new PollingTask.OnTaskListener() {
                    @Override
                    public void executeTask(PollingTask pollingTask) {
                        // TODO: 2019/11/6 请求协议
                        Log.i("LIZHI_LV", "模拟轮询请求协议, isInMainThread = " + isInMainThread());
                        mPollingTask.notifyTaskFinish();
                    }
                }).connected();
    }

    public void cancelTask(){
        Log.i("LIZHI_LV", "cancelTask");
        mPollingTask.destroyTask();
    }

    private boolean isInMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
