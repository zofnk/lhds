package com.lh16808.app.lhds.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.marco.ApiConfig;
import com.lh16808.app.lhds.other.ShowBannerInfo;

public class LottoActivity extends BaseActivity {

    private WebView mWebView;
    private ShowBannerInfo mShowBannerInfo;
    private ProgressBar mProgressBar;
    String baseUrl;

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_lotto_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_lotto_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lotto);
        Intent intent = getIntent();
        String web_key = intent.getStringExtra("web_key");
        if (TextUtils.isEmpty(web_key)) {
            baseUrl = ApiConfig.getBaseUrl(ApiConfig.KaiJianRQURL);
        } else {
            baseUrl = ApiConfig.getBaseUrl(ApiConfig.KaiJianFXURL);
        }
        mWebView = (WebView) findViewById(R.id.web_lotto);
        mProgressBar = (ProgressBar) findViewById(R.id.pro_lotto);
        mProgressBar.setMax(100);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() != View.VISIBLE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mWebView.loadUrl(baseUrl);
    }


    @Override
    protected void loadData() {

    }


}
