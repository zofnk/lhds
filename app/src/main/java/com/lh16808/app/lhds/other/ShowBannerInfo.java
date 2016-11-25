package com.lh16808.app.lhds.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.lh16808.app.lhds.model.BannerUrl;
import com.lh16808.app.lhds.utils.ImageLoader;
import com.lh16808.app.lhds.utils.ScreenUtils;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ShowBannerInfo {
    ArrayList<BannerUrl> listimg = new ArrayList<>();
    private ArrayList<ImageView> advertImgs = new ArrayList<ImageView>();
    private Context context;

    private View vp_fragment__relat_banner;
    private ViewPager vp_banner;
    private int type;

    public ShowBannerInfo(Context context, View v, ViewPager vp_banner, int type) {
        this.context = context;
        this.vp_fragment__relat_banner = v;
        this.vp_banner = vp_banner;
        this.type = type;
        initGGAOJSOn();
    }

    public ShowBannerInfo(Context context, View v, ViewPager vp_banner) {
        this.context = context;
        this.vp_fragment__relat_banner = v;
        this.vp_banner = vp_banner;
        initGGAOJSOn();
    }

    private void initGGAOJSOn() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vp_banner.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenWidth(context) * 75 / 410;
        vp_banner.setLayoutParams(layoutParams);
        H.adBanner(type, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String arg0 = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(arg0);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String titlepic = jsonObject.getString("titlepic");
                        BannerUrl bannerUrl = new BannerUrl();
                        bannerUrl.setTitle(title);
                        bannerUrl.setTitlepic(titlepic);
                        listimg.add(bannerUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (listimg.size() > 0) {
                    vp_fragment__relat_banner.setVisibility(View.VISIBLE);
                    showBannerInfo();
                } else {
                    vp_fragment__relat_banner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showBannerInfo() {
        if (listimg.size() == 2) {
            listimg.addAll(listimg);
        }
        for (int i = 0; i < listimg.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.FIT_XY);
            final BannerUrl advertBean = listimg.get(i);
            if (!((Activity) context).isFinishing())
                ImageLoader.LoaderNet(context, listimg.get(i).getTitlepic(), imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (advertBean.getTitle().length() > 3) {
                            // 调用浏览器
                            Uri webViewUri = Uri.parse(advertBean.getTitle());
                            Intent intent = new Intent(Intent.ACTION_VIEW, webViewUri);
                            context.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            advertImgs.add(imageView);
        }
        if (listimg.size() == 1) {
            bannerMax = 1;
        }
        AdvertPagerAdapter pagerAdapter = new AdvertPagerAdapter(advertImgs, listimg);
        vp_banner.setAdapter(pagerAdapter);
        if (listimg.size() > 1) {
            // vp_banner.setOnTouchListener(new PagerTouchListener(vp_banner));
            vp_banner.addOnPageChangeListener(new myBanner());
            if (vp_banner != null) {
                vp_banner.post(action);
            }
            vp_banner.setCurrentItem(bannerMax / 2);
        }
    }

    private int bannerMax = 10000000;

    private boolean isDragging;

    class myBanner implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isDragging = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    isDragging = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    isDragging = false;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }

    }

    /**
     * 设置轮播自动滑动及速度
     */
    private Runnable action = new Runnable() {
        @Override
        public void run() {
            if (!isDragging) {
                int currentItem = vp_banner.getCurrentItem();
                vp_banner.setCurrentItem(currentItem + 1, true);
            }
            vp_banner.postDelayed(action, 3000);
        }
    };

    class AdvertPagerAdapter extends PagerAdapter {

        private ArrayList<ImageView> mImageList;
        private ArrayList<BannerUrl> mAdvertBeans;

        public AdvertPagerAdapter(ArrayList<ImageView> imageList, ArrayList<BannerUrl> advertBeans) {
            this.mImageList = imageList;
            this.mAdvertBeans = advertBeans;
        }

        @Override
        public int getCount() {
            return bannerMax;
        }

        // 滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        // 每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).removeView(mImageList.get(position % mAdvertBeans.size()));
            ((ViewPager) container).addView(mImageList.get(position % mAdvertBeans.size()));
            return mImageList.get(position % mAdvertBeans.size());
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    public void removeAction() {
        if (listimg.size() > 1) {
            vp_banner.removeCallbacks(action);
        }
    }

    public void runAction() {
        if (listimg.size() > 1) {
            vp_banner.post(action);
        }
    }
}
