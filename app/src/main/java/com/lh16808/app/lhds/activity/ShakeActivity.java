package com.lh16808.app.lhds.activity;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.engine.ShakeListener;
import com.lh16808.app.lhds.marco.Constants;
import com.lh16808.app.lhds.utils.SharedPreUtils;

public class ShakeActivity extends BaseActivity {

    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;
    private ImageView shakeBg;
    private LinearLayout ll_num_bg;
    private TextView tv_lottery_num1, tv_lottery_num2, tv_lottery_num3;
    /**
     * 是否能夠搖一搖
     */
    private boolean isCanShake = false;

    int num1;
    int num2;
    int num3;

    @Override
    protected void initVariables() {
    }

    public void state(int visibie) {

        shakeBg.setVisibility(visibie);
        ll_num_bg.setVisibility(visibie);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shake);
        setActionbar();
        isCanShake = SharedPreUtils.getBoolean(Constants.CAN_SHAKE, true);
        //drawerSet ();//设置  drawer监听    切换 按钮的方向
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);

        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
        shakeBg = (ImageView) findViewById(R.id.shakeBg);
        ll_num_bg = (LinearLayout) findViewById(R.id.ll_num_bg);
        tv_lottery_num1 = (TextView) findViewById(R.id.tv_lottery_num1);
        tv_lottery_num2 = (TextView) findViewById(R.id.tv_lottery_num2);
        tv_lottery_num3 = (TextView) findViewById(R.id.tv_lottery_num3);
        shakeState(isCanShake);

        mShakeListener = new ShakeListener(ShakeActivity.this);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                if (isCanShake) {
                    startAnim();  //开始 摇一摇手掌动画
                    mShakeListener.stop();

                    startVibrato(); //开始 震动
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playMusic(R.raw.shake_match);
                            shakeState(true);
                            state(View.VISIBLE);
                            SharedPreUtils.putString(Constants.SHAKE_RESULT, num1 + "," + num2 + "," + num3);
                            SharedPreUtils.putBoolean(Constants.CAN_SHAKE, false);
                            mVibrator.cancel();
                            mShakeListener.start();
                        }
                    }, 2000);
                }
            }
        });

    }

    public void shakeState(boolean bo) {
        if (bo) {
            state(View.GONE);
            num1 = (int) (Math.random() * 10);
            num2 = (int) (Math.random() * 10);
            num3 = (int) (Math.random() * 10);
        } else {
            String str[] =  SharedPreUtils.getString(Constants.SHAKE_RESULT, "").split(",");
            num1 = Integer.parseInt(str[0]);
            num2 = Integer.parseInt(str[1]);
            num3 = Integer.parseInt(str[2]);
            state(View.VISIBLE);

        }
        tv_lottery_num1.setText("" + num1);
        tv_lottery_num2.setText("" + num2);
        tv_lottery_num3.setText("" + num3);
    }


    @Override
    protected void loadData() {

    }

    private void setActionbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        if (toolbar != null) {
//            toolbar.setTitle("");
//        }
//        setSupportActionBar(toolbar);
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText("搖一搖");
        }
        findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyDialog my = new MyDialog(ShakeActivity.this);
//                my.show(getSupportFragmentManager(), null);
            }
        });
    }

    public void startAnim() {   //定义摇一摇动画动画
        shakeBg.setVisibility(View.GONE);
        ll_num_bg.setVisibility(View.GONE);
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimup0.setDuration(1000);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimup1.setDuration(1000);
        mytranslateanimup1.setStartOffset(1000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        mImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimdn0.setDuration(1000);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimdn1.setDuration(1000);
        mytranslateanimdn1.setStartOffset(1000);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        mImgDn.startAnimation(animdn);


    }

    public void startVibrato() {
        playMusic(R.raw.shake_sound_male);
        //定义震动
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }

    public void playMusic(int res) {
        MediaPlayer player = MediaPlayer.create(this, res);
        player.setLooping(false);
        player.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }
}
