package com.lh16808.app.lhds.widget.imagelook.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.marco.ApiConfig;

import java.util.ArrayList;


/**
 * 图片查看器
 */
public class ImagePagerActivity extends FragmentActivity implements OnClickListener {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;

    /**
     * 描述
     */
    String des;

    // private ImageView back;
    // private TextView title;
    // private RelativeLayout rlTitle;
    /**
     * type == 0 查看网络图片，type== 1 查看本地图片
     */
    private int type = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zorasun_image_detail_pager);

        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        des = getIntent().getStringExtra("des");
        type = getIntent().getIntExtra("type", 0);
        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        //

        indicator = (TextView) findViewById(R.id.indicator);

        // back = (ImageView) findViewById(R.id.btn_left);
        // title = (TextView) findViewById(R.id.txt_title);
        // rlTitle = (RelativeLayout) findViewById(R.id.rlTitle);
        // back.setOnClickListener(this);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        // title.setText(text);
        indicator.setText(des + "(" + text + ")");// 修改

//        if (des != null) {
//            indicator.setText(des);
//        } else {
//            indicator.setText("");
//        }

        // indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(des + "(" + text + ")");// 修改
                // title.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);

            if (type == 0)
                return ImageDetailFragment.newInstance(!url.startsWith("/storage") ? ApiConfig.getImageUrl(url) : url,
                        type, indicator);
            else {
                return ImageDetailFragment.newInstance(url, type);
            }
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            // case R.id.btn_left:
            // finish();
            // break;
            // default:
            // break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // com.nostra13.universalimageloader.core.ImageLoader.getInstance()
        // .clearMemoryCache();

    }
}
