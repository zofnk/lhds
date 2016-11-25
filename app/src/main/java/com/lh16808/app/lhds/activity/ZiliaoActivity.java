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
import com.lh16808.app.lhds.MainActivity;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.fragment.CategoryFragment;
import com.lh16808.app.lhds.fragment.ZiliaoFragment;
import com.lh16808.app.lhds.other.ShowBannerInfo;

/**
 * 10,心水资料,,
 * 11,文字资料,,
 * 7,高手资料,,
 * 12,公式资料,,
 * 13,精品杀项,,
 * 67,香港挂牌,,
 * 14,全年资料,,
 * 83,六合属性,,
 */
public class ZiliaoActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ShowBannerInfo mShowBannerInfo;
    private PagerSlidingTabStrip tabs;
    private final String[] TITLES = {"心水资料", "文字资料", "高手资料", "公式资料", "精品杀项", "香港挂牌", "全年资料"};//, "六合属性"

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_ziLiao_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_ziLiao_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ziliao);
        ViewPager pager = (ViewPager) findViewById(R.id.vp_ziLiao);
        FragmentManager fm = getSupportFragmentManager();
        pager.setAdapter(new ZiliaoActivity.MyPageAdapter(fm));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_ziLiao);
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
            return new ZiliaoFragment(arg0);
        }
    }
}
