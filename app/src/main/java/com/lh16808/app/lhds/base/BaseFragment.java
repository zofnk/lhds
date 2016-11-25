package com.lh16808.app.lhds.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lh16808.app.lhds.base.listener.OnBaseUIListener;
import com.lh16808.app.lhds.fragment.MineFragment;

/**
 * Created by Administrator on 2016/11/12.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootview;
    protected OnBaseUIListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getActivity().getClass().getName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootview == null) {
            mRootview = inflater.inflate(setLayout(), null);
            init();
        } else {
            load();
        }
        return mRootview;
    }

    public void setListener(OnBaseUIListener listener) {
        this.listener = listener;
    }

    protected abstract int setLayout();

    protected abstract void init();

    protected abstract void load();
}
