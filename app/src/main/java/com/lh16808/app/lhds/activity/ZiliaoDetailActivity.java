package com.lh16808.app.lhds.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.model.BannerUrl;
import com.lh16808.app.lhds.model.CateDetailModel;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.other.ShowBannerInfo;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.dataUtils.DateFormatUtils;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ZiliaoDetailActivity extends BaseActivity {

    private CateDetailModel cdm;
    private TextView mTvTitle;
    private TextView mTvTime;
    private TextView mTvContent;
    private RelativeLayout rl_health_viewpg;
    ArrayList<BannerUrl> listimg = new ArrayList<>();

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ziliao_detail);
        cdm = (CateDetailModel) getIntent().getSerializableExtra("CateDetailModel");
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvContent = (TextView) findViewById(R.id.tv_content);
//        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTitle.setText(cdm.getTitle());
        mTvTime.setText(DateFormatUtils.formatWithYMDHms(Long.parseLong(cdm.getDesTitle()) * 1000));
        View viewById = findViewById(R.id.rl_zlInfo_banner);
        ViewPager viewById1 = (ViewPager) findViewById(R.id.vp_zlInfo_banner);
        new ShowBannerInfo(this, viewById, viewById1);
    }

    @Override
    protected void loadData() {
        MyProgressDialog.dialogShow(this);
        RequestParams p = new RequestParams();
        p.put("enews", "zhiliaoShow");
        p.put("sid", cdm.getUrl());
        H.TuKu(p, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int on = jsonObject.getInt("on");
                    if (on == 1) {
                        String newstext = jsonObject.getString("newstext");
                        mTvContent.setText(Html.fromHtml(newstext));
                    } else {
                        ToastUtil.toastShow(ZiliaoDetailActivity.this, "未找到内容~");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                ToastUtil.toastShow(ZiliaoDetailActivity.this, "未找到内容~");
            }
        });
    }


}
