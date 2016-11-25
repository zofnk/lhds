package com.lh16808.app.lhds.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.fragment.MysteryFragment;
import com.lh16808.app.lhds.other.ShowBannerInfo;

public class MysteryActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ShowBannerInfo mShowBannerInfo;
    private PagerSlidingTabStrip tabs;
    private final String[] TITLES = {"曾道人", "白小姐", "内部资料"};

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_mystery_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_mystery_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mystery);
        ViewPager pager = (ViewPager) findViewById(R.id.vp_mystery);
        FragmentManager fm = getSupportFragmentManager();
        pager.setAdapter(new MysteryActivity.MyPageAdapter(fm));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_mystery);
        tabs.setViewPager(pager);
    }

    @Override
    protected void loadData() {

    }

    private final class MyPageAdapter extends FragmentPagerAdapter {
        private MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int arg0) {
            return new MysteryFragment(arg0);
        }
    }

}
