package com.lh16808.app.lhds.base.listener;

/**
 * Created by Administrator on 2016/11/18.
 */
public interface OnLoginLinstener extends OnBaseUIListener {

    void setCurrentUI(int position);

    void finishActivity();

    void setViewPagerGesture(boolean gesture);

    void registerSuccessCallback(String phone, String password);
}
