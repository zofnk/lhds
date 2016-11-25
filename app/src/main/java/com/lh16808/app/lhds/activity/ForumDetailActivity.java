package com.lh16808.app.lhds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.marco.ApiConfig;
import com.lh16808.app.lhds.model.ForumDetailModel;
import com.lh16808.app.lhds.model.ForumModel;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.ImageLoader;
import com.lh16808.app.lhds.utils.MyUtils;
import com.lh16808.app.lhds.utils.StatusBarCompat;
import com.lh16808.app.lhds.utils.http.H;
import com.lh16808.app.lhds.widget.EmoticonsEditText;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ForumDetailActivity extends AppCompatActivity {

    private ForumDetailModel forumDetailModel;
    private TextView tvSum;
    private TextView tvTime;
    private TextView tvFenShu;
    private TextView tvDengJi;
    private TextView tvName;
    private ImageView imgIco;
    private TextView tvTitle;
    private TextView tvText;
    private TextView tvHuifu;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);
        MyUtils.setStatusBarTranslucent(this);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimary));
        iniUI();
        Intent intent = getIntent();
        if (intent != null) {
            ForumModel forumModel = (ForumModel) intent.getSerializableExtra("forumModel");
            if (forumModel != null) {
                initData(forumModel);
            }
        }
    }

    private void iniUI() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("贴子内容");
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(ApiConfig.getRandomCheeseDrawable()).centerCrop().into(imageView);

        tvSum = (TextView) findViewById(R.id.tv_forum_sum);
        tvTime = (TextView) findViewById(R.id.tv_forum_time);
        tvFenShu = (TextView) findViewById(R.id.tv_forum_fenShu);
        tvDengJi = (TextView) findViewById(R.id.tv_forum_dengJi);
        tvName = (TextView) findViewById(R.id.tv_forum_name);
        imgIco = (ImageView) findViewById(R.id.img_forum_ico);
        tvTitle = (TextView) findViewById(R.id.tv_forumDetail_title);
        tvText = (TextView) findViewById(R.id.tv_forumDetail_text);
        tvHuifu = (TextView) findViewById(R.id.tv_forum_huiFu);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_huiFu);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForumDetailActivity.this, HuiFuActivity.class));
            }
        });
    }

    private void initData(ForumModel forumModel) {
        MyProgressDialog.dialogShow(this);
        tvName.setText(forumModel.getUsername());
        tvTime.setText(forumModel.getNewstime());
        tvSum.setText("点击:" + forumModel.getOncliclk() + "次");
        ImageLoader.LoaderNetHead(this, forumModel.getUserpic(), imgIco);
        tvFenShu.setText(forumModel.getUserfen());
        tvDengJi.setText(forumModel.getGroupname());
        tvTitle.setText(forumModel.getTitle());
        tvHuifu.setText(String.valueOf(forumModel.getRnum()));
        RequestParams params = new RequestParams();
        params.add("enews", "bbsshow");
        params.add("sid", forumModel.getId());
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int on = jsonObject.getInt("on");
                    String id = jsonObject.getString("sid");
                    String newstime = jsonObject.getString("newstime");
                    String newstext = jsonObject.getString("newstext");
                    forumDetailModel = new ForumDetailModel(newstext, on, id, newstime);
                    tvText.setText(Html.fromHtml(newstext));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
            }
        });
    }
}
