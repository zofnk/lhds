package com.lh16808.app.lhds.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lh16808.app.lhds.MainActivity;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.activity.CardActivity;
import com.lh16808.app.lhds.activity.LottoActivity;
import com.lh16808.app.lhds.activity.LuckPanActivity;
import com.lh16808.app.lhds.activity.NatureActivity;
import com.lh16808.app.lhds.activity.ShakeActivity;
import com.lh16808.app.lhds.activity.XunBaoActivity;
import com.lh16808.app.lhds.adapter.AnalyseAdapter;
import com.lh16808.app.lhds.other.ShowBannerInfo;
import com.lh16808.app.lhds.utils.MyUtils;

public class AnalyseFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View view;
    ShowBannerInfo mShowBannerInfo;
    AnalyseAdapter mAdapter;
    MainActivity mActivity;

    protected void initVariables() {
        mActivity = MainActivity.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_analyse);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mAdapter = new AnalyseAdapter(getActivity());
        mAdapter.setOnItemClickLitener(new AnalyseAdapter.OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                if (position == 0) {
                    intent.setClass(mActivity, LottoActivity.class);
                    intent.putExtra("web_key", "web_zoushi");
                } else if (position == 1) {
                    intent.setClass(mActivity, NatureActivity.class);
                } else if (position == 2) {
                    intent.setClass(mActivity, LuckPanActivity.class);
                } else if (position == 3) {
                    intent.setClass(mActivity, CardActivity.class);
                } else if (position == 4) {
                    intent.setClass(mActivity, ShakeActivity.class);
                } else {
                    intent.setClass(mActivity, XunBaoActivity.class);
                }
                mActivity.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        View rlBanner = view.findViewById(R.id.rl_analyse_banner);
        ViewPager vpBanner = (ViewPager) view.findViewById(R.id.vp_analyse_banner);
        mShowBannerInfo = new ShowBannerInfo(MainActivity.getInstance(), rlBanner, vpBanner);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_analyse, container, false);
            loadData();
            initVariables();
        } else {
            mShowBannerInfo.runAction();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mShowBannerInfo.removeAction();
    }

    protected void loadData() {

    }
}
