package com.lh16808.app.lhds.base;

import android.app.Activity;
import android.app.Application;

import com.lh16808.app.lhds.marco.ApiConfig;
import com.lh16808.app.lhds.utils.ScreenUtils;
import com.lh16808.app.lhds.utils.SharedPreUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */

public class BaseAPP extends Application {

    private static BaseAPP mContext;
    private List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        activityList = new ArrayList<>();
        SharedPreUtils.init(this);
        ApiConfig.init(this);
    }

    public static BaseAPP getInstance() {
        return mContext;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
