package com.lh16808.app.lhds.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.lh16808.app.lhds.marco.Constants;
import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.utils.dataUtils.DateFormatUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/11/15.
 */

public class MyUtils {
    private static final String TAG = MyUtils.class.getName();

    public interface OnNetTimeOut {
        void timeOut();

        void error();
    }

    private static long mLastClickTime = 0;             // 按钮最后一次点击时间
    private static final int SPACE_TIME = 600;          // 空闲时间

    /**
     * 是否连续点击按钮多次
     *
     * @return 是否快速多次点击
     */
    public static boolean isFastDoubleClick() {
        long time = SystemClock.elapsedRealtime();
        if (time - mLastClickTime <= SPACE_TIME) {
            return true;
        } else {
            mLastClickTime = time;
            return false;
        }
    }

    public static void backToOriginal(View view) {
        //获取当前位置
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);

        //上移
        ObjectAnimator animatorMove = ObjectAnimator.ofFloat(view, "translationY", 0f);
        //渐隐
        ObjectAnimator animatorFadein = ObjectAnimator.ofFloat(view, "alpha", 1.0f);

        AnimatorSet set = new AnimatorSet();
        set.play(animatorMove).with(animatorFadein);
        set.setDuration(600).start();
    }

    public static void upToWindowTop(View view, View pointView) {
        //获取当前位置
        int[] viewLocation = new int[2];
        pointView.getLocationOnScreen(viewLocation);
        //上移
        ObjectAnimator animatorMove = ObjectAnimator.ofFloat(view, "translationY", -viewLocation[1]);

        AnimatorSet set = new AnimatorSet();
        set.play(animatorMove);
        set.setDuration(700).start();

    }

    public static void upToOutofWindow(View view) {
        //获取当前位置
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);

        //上移
        ObjectAnimator animatorMove = ObjectAnimator.ofFloat(view, "translationY", -viewLocation[1]);
        //渐隐
        ObjectAnimator animatorFadeout = ObjectAnimator.ofFloat(view, "alpha", 0f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorMove).with(animatorFadeout);
        set.setDuration(400).start();
    }

    public static long getTotalTime() {
        String nowTime = DateFormatUtils.getCurrentTime();
        String lotteryTime = DateFormatUtils.formatWithYMDHms(Lottery.RefreshTime * 1000);
        if (Lottery.RefreshTime == 0) {
            return 0;
        }
        Log.e(TAG, "nowTime:" + nowTime + "lotteryTime" + lotteryTime);
        //計算出當前時間離開獎時間的時間差（毫秒單位）
        return DateFormatUtils.getTimeDifference(nowTime, lotteryTime);
    }

    public static int SXSJ() {
        if (Lottery.RefreshTime > 0) {
            long totalTime = MyUtils.getTotalTime();
            Log.e(TAG, "totalTime:" + totalTime);
            if (totalTime > 600000) {
                return 30000;
            }
            return 5000;
        }
        return 5000;
    }

    public static void setStatusBarTranslucent(Activity activity) {
//        // 如果版本在4.4以上
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 状态栏透明
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            }
        }
    }

    public static int isRBG(String str) {
        int parseInt = Integer.parseInt(str);
        for (int i = 0; i < Constants.bulebo.length; i++) {
            if (parseInt == Constants.bulebo[i]) {
                return Constants.rbg[1];
            }
        }
        for (int i = 0; i < Constants.greenbo.length; i++) {
            if (parseInt == Constants.greenbo[i]) {
                return Constants.rbg[2];
            }
        }
        return Constants.rbg[0];
    }

    public static String[] getYear() {
        // ArrayList<String> yearList = new ArrayList<>();
        int i = MyUtils.GetYear();
        int i2 = 1975;
        String[] provinceList = new String[i - i2];
        for (int j = 0; j < i - i2; j++) {
            provinceList[j] = (i - j + "");
        }
        return provinceList;
    }

    public static int GetYear() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        return y;
    }
}
