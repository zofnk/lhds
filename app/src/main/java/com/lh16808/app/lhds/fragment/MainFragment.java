package com.lh16808.app.lhds.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lh16808.app.lhds.MainActivity;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.activity.ForumActivity;
import com.lh16808.app.lhds.activity.HistoryRecordActivity;
import com.lh16808.app.lhds.activity.KJVideoActivity;
import com.lh16808.app.lhds.activity.LottoActivity;
import com.lh16808.app.lhds.activity.MysteryActivity;
import com.lh16808.app.lhds.activity.TuKuActivity;
import com.lh16808.app.lhds.activity.ZiliaoActivity;
import com.lh16808.app.lhds.adapter.MainAdapter;
import com.lh16808.app.lhds.base.BaseAPP;
import com.lh16808.app.lhds.base.BaseFragment;
import com.lh16808.app.lhds.marco.Constants;
import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.other.KaiJianLoad;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.other.ShowBannerInfo;
import com.lh16808.app.lhds.service.LottoService;
import com.lh16808.app.lhds.utils.AppLog;
import com.lh16808.app.lhds.utils.MyUtils;
import com.lh16808.app.lhds.utils.SharedPreUtils;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.dataUtils.DateFormatUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    public static String TAG = MainFragment.class.getName();

    Class[] classes = {HistoryRecordActivity.class, LottoActivity.class, ForumActivity.class, MysteryActivity.class, ZiliaoActivity.class, TuKuActivity.class};
    private View view;
    private RecyclerView mRecyclerView;
    private TextView mTvLotteryTimeDay;
    private TextView mTvLotteryTimeHour;
    private TextView mTvLotteryTimeMinute;
    private TextView mTvLotteryTimeSecond;
    private TextView mTvLotteryTime;
    private TextView tv_z1m;
    private TextView tv_z2m;
    private TextView tv_z3m;
    private TextView tv_z4m;
    private TextView tv_z5m;
    private TextView tv_z6m;
    private TextView tv_tm;
    private TextView tv_z1sx;
    private TextView tv_z2sx;
    private TextView tv_z3sx;
    private TextView tv_z4sx;
    private TextView tv_z5sx;
    private TextView tv_z6sx;
    private TextView tv_tmsx;
    private MediaPlayer mediaPlayer;

    private KaiJianLoad kaiJianLoad = new KaiJianLoad();
    private Handler handler = new Handler();
    private CountDownTimer mCountDownTimer;
    private ShowBannerInfo mShowBannerInfo;
    private View mLLBaoMa;
    private View mLLKaiJian;
    private TextView mTvBaoMa;
    public static Lottery mLottery;
    private MainActivity mActivity;
    private Intent mService;
    LottoService.LottoBinder mBinder;
    private TextView tvResultBQ;

    public MainFragment() {
    }

    protected void initVariables() {
        View rlBanner = view.findViewById(R.id.rl_main_banner);
        ViewPager vpBanner = (ViewPager) view.findViewById(R.id.vp_main_banner);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        MainAdapter mainAdapter = new MainAdapter(getActivity());
        mainAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override   
            public void onItemClick(View view, int position) {
                MainActivity instance = MainActivity.getInstance();
                instance.startActivity(new Intent(instance, classes[position]));
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mainAdapter);
        mTvLotteryTimeDay = (TextView) view.findViewById(R.id.tv_kjsj_tian);
        mTvLotteryTimeHour = (TextView) view.findViewById(R.id.tv_kjsj_shi);
        mTvLotteryTimeMinute = (TextView) view.findViewById(R.id.tv_kjsj_fen);
        mTvLotteryTimeSecond = (TextView) view.findViewById(R.id.tv_kjsj_miao);
        mTvLotteryTime = (TextView) view.findViewById(R.id.tv_kjsj);
        tv_z1m = (TextView) view.findViewById(R.id.tv_z1m);
        tv_z2m = (TextView) view.findViewById(R.id.tv_z2m);
        tv_z3m = (TextView) view.findViewById(R.id.tv_z3m);
        tv_z4m = (TextView) view.findViewById(R.id.tv_z4m);
        tv_z5m = (TextView) view.findViewById(R.id.tv_z5m);
        tv_z6m = (TextView) view.findViewById(R.id.tv_z6m);
        tv_tm = (TextView) view.findViewById(R.id.tv_tm);
        tv_z1sx = (TextView) view.findViewById(R.id.tv_z1sx);
        tv_z2sx = (TextView) view.findViewById(R.id.tv_z2sx);
        tv_z3sx = (TextView) view.findViewById(R.id.tv_z3sx);
        tv_z4sx = (TextView) view.findViewById(R.id.tv_z4sx);
        tv_z5sx = (TextView) view.findViewById(R.id.tv_z5sx);
        tv_z6sx = (TextView) view.findViewById(R.id.tv_z6sx);
        tv_tmsx = (TextView) view.findViewById(R.id.tv_tmsx);
        mLLBaoMa = view.findViewById(R.id.kj_main_ll_2);
        mLLKaiJian = view.findViewById(R.id.kj_main_ll_1);
        mTvBaoMa = (TextView) view.findViewById(R.id.tv_baoma_tx);
        mLLBaoMa.setOnClickListener(this);
        mShowBannerInfo = new ShowBannerInfo(getActivity(), rlBanner, vpBanner, 1);
        tvResultBQ = (TextView) view.findViewById(R.id.tv_main_Result_bq);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            mActivity = MainActivity.getInstance();
            initVariables();
            loadData();
            load();
        } else {
            mShowBannerInfo.runAction();
        }
        AppLog.redLog(TAG, "onCreateView");
        return view;
    }

    private void load() {
        mActivity.setLoadzt(new MainActivity.LoadZT() {
            @Override
            public void loadzt(int zt) {
                AppLog.redLog(TAG + "sendZT", "zt:" + zt);
                if (zt == 1) {
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 2) {
                    playGG(R.raw.zero);
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_zbb_2));
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 4) {
                    playGG(R.raw.ggz);
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_ggz_4));
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 5) {
                    playGG(R.raw.zcrjhz);
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_zzk_5));
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 3) {
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_zzk_3));
                    startActivity();
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                }
                if (zt != 1) {
                    if (mLLBaoMa.getVisibility() != View.VISIBLE) {
                        mLLBaoMa.setVisibility(View.VISIBLE);
                        mLLKaiJian.setVisibility(View.GONE);
                        AppLog.redLog(TAG + "sendZT", "x2");
                    }
                } else {
                    if (mLLKaiJian.getVisibility() != View.VISIBLE) {
                        mLLBaoMa.setVisibility(View.GONE);
                        mLLKaiJian.setVisibility(View.VISIBLE);
                        loadData();
                        AppLog.redLog("KJ" + "sendZT", "x1");
                    }
                }
                AppLog.redLog(TAG + "sendZT", "" + zt + "---" + Lottery.getLottery().toString() + "---sxsj:" + Lottery.Time);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mShowBannerInfo.removeAction();
    }

    protected void loadData() {
        MyProgressDialog.dialogShow(mActivity);
        mLottery = Lottery.getLottery();
        kaiJianLoad.setOnNetData(new KaiJianLoad.OnNetData() {
            @Override
            public void onScuss() {
                MyProgressDialog.dialogHide();
                Data();
                if ("1".equals(mLottery.zt)) {
                    mLLBaoMa.setVisibility(View.GONE);
                    mLLKaiJian.setVisibility(View.VISIBLE);
                } else {
                    mLLBaoMa.setVisibility(View.VISIBLE);
                    mLLKaiJian.setVisibility(View.GONE);
                }
                String upLotteryTime = SharedPreUtils.getString(Constants.LOTTERY_TIME, "0");
                String nowLotteryTime = Lottery.getLottery().bq;
                if (!upLotteryTime.equals(nowLotteryTime)) {
                    //保持開獎時間，新的一期能夠進行新的抽獎   String lotteryTime = getLotteryTime();
                    SharedPreUtils.putString(Constants.LOTTERY_TIME, nowLotteryTime);
                    SharedPreUtils.putBoolean(Constants.CAN_LUCK_PAN, true);
                    SharedPreUtils.putBoolean(Constants.CAN_TURN_CARDS, true);
                    SharedPreUtils.putBoolean(Constants.CAN_SHAKE, true);
                }
            }
            @Override
            public void onError() {
                MyProgressDialog.dialogHide();
            }

            @Override
            public void onTime() {
                if ("1".equals(mLottery.zt))
                    countDown();
            }
        });
    }

    private void Data() {
        tvResultBQ.setText(mLottery.bq + "期开奖结果");
        mTvLotteryTime.setText("" + mLottery.xyqsx + "星期" + mLottery.xq);
        if (mLottery.zt.equals("1")) {
            tv_z1m.setBackgroundResource(MyUtils.isRBG(mLottery.z1m));
            tv_z2m.setBackgroundResource(MyUtils.isRBG(mLottery.z2m));
            tv_z3m.setBackgroundResource(MyUtils.isRBG(mLottery.z3m));
            tv_z4m.setBackgroundResource(MyUtils.isRBG(mLottery.z4m));
            tv_z5m.setBackgroundResource(MyUtils.isRBG(mLottery.z5m));
            tv_z6m.setBackgroundResource(MyUtils.isRBG(mLottery.z6m));
            tv_tm.setBackgroundResource(MyUtils.isRBG(mLottery.tm));
            tv_z1m.setText(mLottery.z1m);
            tv_z2m.setText(mLottery.z2m);
            tv_z3m.setText(mLottery.z3m);
            tv_z4m.setText(mLottery.z4m);
            tv_z5m.setText(mLottery.z5m);
            tv_z6m.setText(mLottery.z6m);
            tv_tm.setText(mLottery.tm);
            tv_z1sx.setText(mLottery.z1sx);
            tv_z2sx.setText(mLottery.z2sx);
            tv_z3sx.setText(mLottery.z3sx);
            tv_z4sx.setText(mLottery.z4sx);
            tv_z5sx.setText(mLottery.z5sx);
            tv_z6sx.setText(mLottery.z6sx);
            tv_tmsx.setText(mLottery.tmsx);
        }
    }

    /**
     * 倒計時
     */
    private void countDown() {
        AppLog.redLog(TAG, "countDown");
        mCountDownTimer = null;
        long totalTime = MyUtils.getTotalTime();
        int countDownInterval = 1000;
        mCountDownTimer = new CountDownTimer(totalTime, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                //將毫秒改成時間單位
                String times[] = DateFormatUtils.getTotalTime(millisUntilFinished);
                mTvLotteryTimeDay.setText(times[0]);
                mTvLotteryTimeHour.setText(times[1]);
                mTvLotteryTimeMinute.setText(times[2]);
                mTvLotteryTimeSecond.setText(times[3]);
            }

            @Override
            public void onFinish() {
                openLottery();
            }
        };
        mCountDownTimer.start();
    }

    private void openLottery() {
        AppLog.redLog(TAG, "countDown-->openLottery");
        if (mLLBaoMa.getVisibility() != View.VISIBLE) {
            mLLBaoMa.setVisibility(View.VISIBLE);
            mLLKaiJian.setVisibility(View.GONE);
            Lottery.Time = 5000;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kj_main_ll_2:
                if ("3".equals(mLottery.zt)) {
                    startActivity();
                }
                break;
        }
    }

    private void startActivity() {
        ToastUtil.toastShow(mActivity, "xxxxxxxxxxxxxxxx");
        Intent intent = new Intent(getContext(), KJVideoActivity.class);
        getActivity().startActivityForResult(intent, 23);
    }

    private void playGG(int a) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (!MainActivity.getInstance().isFinishing()) {
            mediaPlayer = MediaPlayer.create(MainActivity.getInstance(), a);
            mediaPlayer.start();
        }
    }
}
