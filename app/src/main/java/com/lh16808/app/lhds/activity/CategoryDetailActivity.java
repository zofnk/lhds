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
import com.lh16808.app.lhds.fragment.CategoryDetailFragment;
import com.lh16808.app.lhds.fragment.CategoryFragment;
import com.lh16808.app.lhds.model.CateDetailModel;
import com.lh16808.app.lhds.other.ShowBannerInfo;
import com.lh16808.app.lhds.widget.imagelook.ui.ImagePagerActivity;

import java.util.ArrayList;

public class CategoryDetailActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ShowBannerInfo mShowBannerInfo;
    private PagerSlidingTabStrip tabs;
    private int pagerPosition;
    private String des;
    private int type;
    ArrayList<CateDetailModel> list = new ArrayList<>();

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_cyDetail_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_cyDetail_banner);
        mShowBannerInfo = new ShowBannerInfo(CategoryDetailActivity.this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category_detail);
        initData();
        ViewPager pager = (ViewPager) findViewById(R.id.vp_cyDetail);
        FragmentManager fm = getSupportFragmentManager();
        pager.setAdapter(new CategoryDetailActivity.MyPageAdapter(fm));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_cyDetail);
        tabs.setViewPager(pager);
    }

    private void initData() {
        pagerPosition = getIntent().getIntExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS);
        des = getIntent().getStringExtra("des");
        type = getIntent().getIntExtra("type", 0);
        for (int i = 0; i < urls.size(); i++) {
            list.add(new CateDetailModel("第" + (urls.size() - i) + "期", urls.get(i), des));
        }
    }

    @Override
    protected void loadData() {

    }

    private final class MyPageAdapter extends FragmentPagerAdapter {
//        private final String[] TITLES = {"彩色图库", "玄机彩图", "黑白图库", "全年图库"};

        private MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return new CategoryDetailFragment(arg0, list.get(arg0));
        }
    }
}
