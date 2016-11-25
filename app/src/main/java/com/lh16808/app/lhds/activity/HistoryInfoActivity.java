package com.lh16808.app.lhds.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.marco.Constants;
import com.lh16808.app.lhds.model.HistoryRecordModel;
import com.lh16808.app.lhds.other.ShowBannerInfo;
import com.lh16808.app.lhds.utils.dataUtils.DateFormatUtils;


/**
 * Created by admin on 2016/10/26.
 */
public class HistoryInfoActivity extends BaseActivity {

    private TextView tvDate;
    private TextView tvNum;
    private TextView tvZ1m;
    private TextView tvZ1sx;
    private TextView tvZ2m;
    private TextView tvZ2sx;
    private TextView tvZ3m;
    private TextView tvZ3sx;
    private TextView tvZ4m;
    private TextView tvZ4sx;
    private TextView tvZ5m;
    private TextView tvZ5sx;
    private TextView tvZ6m;
    private TextView tvZ6sx;
    private TextView tvTm;
    private TextView tvTmsx;

    private TextView tvSingleAndDouble;
    private TextView tvSize;
    private TextView tvAllSize;
    private TextView tvAllSingleAndDouble;

    HistoryRecordModel modle;
    private ShowBannerInfo mShowBannerInfo;

    private int isRBG(String str) {
        int parseInt = Integer.parseInt(str);
        for (int i = 0; i < Constants.redbo.length; i++) {
            if (parseInt == Constants.redbo[i]) {
                return 0;
            }
        }
        for (int i = 0; i < Constants.bulebo.length; i++) {
            if (parseInt == Constants.bulebo[i]) {
                return 1;
            }
        }
        for (int i = 0; i < Constants.greenbo.length; i++) {
            if (parseInt == Constants.greenbo[i]) {
                return 2;
            }
        }
        return 0;
    }

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_hisInfo_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_hisInfo_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_history_info);
        modle = (HistoryRecordModel) getIntent().getSerializableExtra("INFO");
        Log.e(getClass().getName(), modle.toString());
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvNum = (TextView) findViewById(R.id.tv_num);
        tvZ1m = (TextView) findViewById(R.id.tv_z1m);
        tvZ1sx = (TextView) findViewById(R.id.tv_z1sx);
        tvZ2m = (TextView) findViewById(R.id.tv_z2m);
        tvZ2sx = (TextView) findViewById(R.id.tv_z2sx);
        tvZ3m = (TextView) findViewById(R.id.tv_z3m);
        tvZ3sx = (TextView) findViewById(R.id.tv_z3sx);
        tvZ4m = (TextView) findViewById(R.id.tv_z4m);
        tvZ4sx = (TextView) findViewById(R.id.tv_z4sx);
        tvZ5m = (TextView) findViewById(R.id.tv_z5m);
        tvZ5sx = (TextView) findViewById(R.id.tv_z5sx);
        tvZ6m = (TextView) findViewById(R.id.tv_z6m);
        tvZ6sx = (TextView) findViewById(R.id.tv_z6sx);
        tvTm = (TextView) findViewById(R.id.tv_tm);
        tvTmsx = (TextView) findViewById(R.id.tv_tmsx);

        tvSingleAndDouble = (TextView) findViewById(R.id.tv_single_and_double);
        tvSize = (TextView) findViewById(R.id.tv_size);
        tvAllSize = (TextView) findViewById(R.id.tv_all_size);
        tvAllSingleAndDouble = (TextView) findViewById(R.id.tv_all_single_and_double);

        tvDate.setText(DateFormatUtils.formatWithYMD2(Long.valueOf(modle.getNewstime()) * 1000));
        tvNum.setText(modle.getBq() + "期");
        tvZ1m.setBackgroundResource(Constants.rbg[isRBG(modle.z1m)]);
        tvZ2m.setBackgroundResource(Constants.rbg[isRBG(modle.z2m)]);
        tvZ3m.setBackgroundResource(Constants.rbg[isRBG(modle.z3m)]);
        tvZ4m.setBackgroundResource(Constants.rbg[isRBG(modle.z4m)]);
        tvZ5m.setBackgroundResource(Constants.rbg[isRBG(modle.z5m)]);
        tvZ6m.setBackgroundResource(Constants.rbg[isRBG(modle.z6m)]);
        tvTm.setBackgroundResource(Constants.rbg[isRBG(modle.tm)]);
        tvZ1m.setText(modle.z1m);
        tvZ2m.setText(modle.z2m);
        tvZ3m.setText(modle.z3m);
        tvZ4m.setText(modle.z4m);
        tvZ5m.setText(modle.z5m);
        tvZ6m.setText(modle.z6m);
        tvTm.setText(modle.tm);
        tvZ1sx.setText(modle.z1sx);
        tvZ2sx.setText(modle.z2sx);
        tvZ3sx.setText(modle.z3sx);
        tvZ4sx.setText(modle.z4sx);
        tvZ5sx.setText(modle.z5sx);
        tvZ6sx.setText(modle.z6sx);
        tvTmsx.setText(modle.tmsx);

        if (Integer.parseInt(modle.tm) % 2 != 0) {
            tvSingleAndDouble.setText("單");
        } else {
            tvSingleAndDouble.setText("雙");
        }
        if (Integer.parseInt(modle.tm) < 25) {
            tvSize.setText("小");
        } else {
            tvSize.setText("大");
        }


        int all = Integer.parseInt(modle.z1m)
                + Integer.parseInt(modle.z2m)
                + Integer.parseInt(modle.z3m)
                + Integer.parseInt(modle.z4m)
                + Integer.parseInt(modle.z5m)
                + Integer.parseInt(modle.z6m)
                + Integer.parseInt(modle.tm);
        if (all < 148) {
            tvAllSize.setText("小");
        } else {
            tvAllSize.setText("大");
        }

        if (all % 2 != 0) {
            tvAllSingleAndDouble.setText("單");
        } else {
            tvAllSingleAndDouble.setText("雙");
        }
    }

    @Override
    protected void loadData() {

    }
}
