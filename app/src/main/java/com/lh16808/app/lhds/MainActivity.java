package com.lh16808.app.lhds;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.fragment.AnalyseFragment;
import com.lh16808.app.lhds.fragment.MainFragment;
import com.lh16808.app.lhds.fragment.MineFragment;
import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.other.KaiJianLoad;
import com.lh16808.app.lhds.service.LottoService;
import com.lh16808.app.lhds.utils.AppLog;
import com.lh16808.app.lhds.widget.FragmentTabHost2;


public class MainActivity extends BaseActivity {

    private int tabsImg[] = new int[]{R.drawable.tab_selector_home, R.drawable.tab_selector_analyse,
            R.drawable.tab_selector_mine};
    public static String TAG = MainActivity.class.getName();
    private static MainActivity mContext;
    private FragmentTabHost2 mTabHost;
    private final Class[] fragments = {MainFragment.class, AnalyseFragment.class,
            MineFragment.class};
    private String tabsString[] = new String[]{"首页", "分析",
            "我的"};
    private Intent mService;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        setActionbar();
        mContext = this;
        mTabHost = (FragmentTabHost2) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < fragments.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(getView(i)), fragments[i], null);
        }
        mService = new Intent(this, LottoService.class);
        startService(mService);
    }

    @Override
    protected void onStart() {
        mService = new Intent(this, LottoService.class);
        bindService(mService, mConn, Context.BIND_AUTO_CREATE);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unbindService(mConn);
        super.onStop();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        stopService(mService);
        super.onDestroy();
    }

    public static MainActivity getInstance() {
        return mContext;
    }

    private View getView(int index) {
        View view = getLayoutInflater().inflate(R.layout.layout_main_tabs, null);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.img_main_tabs);
        TextView textView1 = (TextView) view.findViewById(R.id.tv_main_tabs);
        textView1.setText(tabsString[index]);
        imageView1.setImageResource(tabsImg[index]);
        return view;
    }

    private void setActionbar() {
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
//        mTvYear = (TextView) findViewById(R.id.tv_year);
        if (toolBarTitle != null) {
            toolBarTitle.setText(getResources().getString(R.string.app_name));
        }
        findViewById(R.id.iv_back).setVisibility(View.GONE);
    }


    private LottoService.LottoBinder mBinder;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (LottoService.LottoBinder) service;
            mBinder.lunXun();
            mBinder.setOnVideoPlayPosition(new LottoService.OnVideoPlayPosition() {
                @Override
                public void sendPosition(int a, String zm, String sx) {
                    AppLog.redLog(TAG, "" + "a:" + a + "-zm:" + zm + "-sx:" + sx);
                }

                @Override
                public void sendZT(int zt) {
                    if (MainActivity.this.zt != null) {
                        MainActivity.this.zt.loadzt(zt);
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    LoadZT zt;

    public interface LoadZT {
        void loadzt(int zt);
    }

    public void setLoadzt(LoadZT zt) {
        this.zt = zt;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
