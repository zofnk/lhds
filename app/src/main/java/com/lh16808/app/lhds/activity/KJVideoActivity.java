package com.lh16808.app.lhds.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.fragment.MainFragment;
import com.lh16808.app.lhds.marco.Constants;
import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.other.KaiJianLoad;
import com.lh16808.app.lhds.other.SceneAnimation;
import com.lh16808.app.lhds.other.SceneAnimation2;
import com.lh16808.app.lhds.service.LottoService;
import com.lh16808.app.lhds.utils.MyUtils;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class KJVideoActivity extends BaseActivity {
    ArrayList<View> views = new ArrayList<>();
    ArrayList<TextView> zm_views = new ArrayList<>();
    ArrayList<TextView> sx_views = new ArrayList<>();
    private ImageView imageView1;
    private ImageView img_horn_on;
    private TextView tv_bq_sum;

    private SceneAnimation sceneAnimation;
    //    KaiJianLoad kj;
    private MediaPlayer mediaPlayerX;
    private MediaPlayer mediaPlayer;
    private boolean isMusicStop;
    ArrayList<Integer> arrayList = new ArrayList<>();
    Handler handler = new Handler();
    protected boolean isON;
    Runnable r = new Runnable() {

        @Override
        public void run() {
            int size = arrayList.size();
            if (size > 0 && !isMusicStop) {
                if (mediaPlayer != null) {
                    if (!mediaPlayer.isPlaying()) {
                        isPlaymusicing();
                    }
                } else {
                    isPlaymusicing();
                }
            }
            if (size != 0) {
                handler.postDelayed(r, 500);
            }
        }

    };

    // 方法2 ： 使用mediaplayer播放本地raw/下的audio文件的(包括 setDataSource())
    private void playLocalAudio_usingDescriptor() throws IllegalArgumentException, IllegalStateException, IOException {
        AssetFileDescriptor fileDesc = this.getResources().openRawResourceFd(R.raw.bj_kj_music);
        FileDescriptor fileDescriptor = fileDesc.getFileDescriptor();
        if (fileDesc != null) {
            mediaPlayerX = new MediaPlayer();
            mediaPlayerX.setDataSource(fileDescriptor, fileDesc.getStartOffset(), fileDesc.getLength());
            fileDesc.close();
            mediaPlayerX.prepare();
            mediaPlayerX.setLooping(true);
            mediaPlayerX.start();
        }
    }

    @Override
    protected void initVariables() {
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        img_horn_on = (ImageView) findViewById(R.id.img_horn_on);
        img_horn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isON = !isON;
                if (isON) {
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                    img_horn_on.setImageResource(R.drawable.ic_horn_close);
                } else {
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume / 2, 0);
                    img_horn_on.setImageResource(R.drawable.ic_horn_open);
                }
            }
        });
        tv_bq_sum = (TextView) findViewById(R.id.tv_bq_sum);
        findViewById(R.id.img_cancel_tuichu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setStopMusic();
                finish();
            }
        });
        TextView tv_z1m = (TextView) findViewById(R.id.tv_z1m);
        TextView tv_z2m = (TextView) findViewById(R.id.tv_z2m);
        TextView tv_z3m = (TextView) findViewById(R.id.tv_z3m);
        TextView tv_z4m = (TextView) findViewById(R.id.tv_z4m);
        TextView tv_z5m = (TextView) findViewById(R.id.tv_z5m);
        TextView tv_z6m = (TextView) findViewById(R.id.tv_z6m);
        TextView tv_tm = (TextView) findViewById(R.id.tv_tm);
        zm_views.add(tv_z1m);
        zm_views.add(tv_z2m);
        zm_views.add(tv_z3m);
        zm_views.add(tv_z4m);
        zm_views.add(tv_z5m);
        zm_views.add(tv_z6m);
        zm_views.add(tv_tm);
        TextView tv_z1sx = (TextView) findViewById(R.id.tv_z1sx);
        TextView tv_z2sx = (TextView) findViewById(R.id.tv_z2sx);
        TextView tv_z3sx = (TextView) findViewById(R.id.tv_z3sx);
        TextView tv_z4sx = (TextView) findViewById(R.id.tv_z4sx);
        TextView tv_z5sx = (TextView) findViewById(R.id.tv_z5sx);
        TextView tv_z6sx = (TextView) findViewById(R.id.tv_z6sx);
        TextView tv_tmsx = (TextView) findViewById(R.id.tv_tmsx);
        sx_views.add(tv_z1sx);
        sx_views.add(tv_z2sx);
        sx_views.add(tv_z3sx);
        sx_views.add(tv_z4sx);
        sx_views.add(tv_z5sx);
        sx_views.add(tv_z6sx);
        sx_views.add(tv_tmsx);
        View linear_z1 = findViewById(R.id.linear_z1);
        View linear_z2 = findViewById(R.id.linear_z2);
        View linear_z3 = findViewById(R.id.linear_z3);
        View linear_z4 = findViewById(R.id.linear_z4);
        View linear_z5 = findViewById(R.id.linear_z5);
        View linear_z6 = findViewById(R.id.linear_z6);
        View linear_tm = findViewById(R.id.linear_tm);
        View linear_img = findViewById(R.id.linear_img);
        views.add(linear_z1);
        views.add(linear_z2);
        views.add(linear_z3);
        views.add(linear_z4);
        views.add(linear_z5);
        views.add(linear_z6);
        views.add(linear_tm);
        views.add(linear_img);
        initSonud();
        setAnima();
    }

    private AudioManager manager;
    private int maxVolume;

    private void initSonud() {
        manager = (AudioManager) getSystemService(AUDIO_SERVICE);
        maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_kjvideo);
    }

    private void setinvis(View tv_z3m) {
        tv_z3m.setVisibility(View.INVISIBLE);
    }

    private void initSetData(String[] zm, String[] sx) {
        for (int i = 0; i < zm.length; i++) {
            if (!TextUtils.isEmpty(sx[i])) {
                setvis(views.get(i));
                if (i == 5) {
                    setvis(views.get(7));
                }
                TextView zm_view = zm_views.get(i);
                zm_view.setBackgroundResource(MyUtils.isRBG(zm[i]));
                zm_view.setText(zm[i]);
                sx_views.get(i).setText(sx[i]);
            }
        }
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConn);
        isMusicStop = true;
        setStopMusic();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mService = new Intent(this, LottoService.class);
        bindService(mService, mConn, Context.BIND_AUTO_CREATE);
        isMusicStop = false;
        if (!TextUtils.isEmpty(Lottery.getLottery().tmsx)) {
            finish();
        }
        try {
            playLocalAudio_usingDescriptor();
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    private int getSXid(String sx) {
        for (int i = 0; i < Constants.sxstr.length; i++) {
            if (sx.equals(Constants.sxstr[i])) {
                return i;
            }
        }
        return 0;
    }

    private void isMusicID(String zm, int i, String sx) {
        arrayList.add(Constants.music_sum[i]);
        int parseInt = 0;
        try {
            parseInt = Integer.parseInt(zm);
        } catch (NumberFormatException e) {
            // TODO
            parseInt = 1;
            e.printStackTrace();
        }
        arrayList.add(Constants.music[parseInt]);
        int sXid = getSXid(sx);
        arrayList.add(Constants.musicsx[sXid]);
        imageView1.post(r);
    }

    private void startAnima(View tv_z2m) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tween_kj);
        tv_z2m.startAnimation(animation);
    }

    private void setvis(View tv_z3m) {
        tv_z3m.setVisibility(View.VISIBLE);
    }

    private void setStopMusic() {
        if (mediaPlayer != null) {
            boolean playing = mediaPlayer.isPlaying();
            if (playing) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaPlayerX != null) {
            boolean playing = mediaPlayerX.isPlaying();
            if (playing) {
                mediaPlayerX.stop();
            }
            mediaPlayerX.release();
            mediaPlayerX = null;
        }
    }

    private void isPlaymusicing() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (arrayList.size() > 0) {
            mediaPlayer = MediaPlayer.create(this, arrayList.get(0));
            mediaPlayer.setVolume(1, 1);
            mediaPlayer.start();
            arrayList.remove(0);
        }
        if (mBinder != null) {
            String[] listSX = mBinder.getListAll();
            if (arrayList.size() == 0 && !TextUtils.isEmpty(listSX[listSX.length - 1])) {
                playStop();
            }
        }
    }

    private void playStop() {
        if (sceneAnimation != null) {
            sceneAnimation.reomve();
        }
        new SceneAnimation2(imageView1, Constants.ed, 100, this);
    }

    private void setAnima() {
        Lottery mLottery = MainFragment.mLottery;
        mediaPlayer = MediaPlayer.create(this, Constants.music[0]);
        if (mLottery.zt.equals("3") && TextUtils.isEmpty(mLottery.tmsx)) {
            mediaPlayer.start();
            sceneAnimation = new SceneAnimation(imageView1, Constants.sa, 100);
            imageView1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sceneAnimation.setFrameRess(Constants.ca);
                }
            }, 2550);
        } else {
            sceneAnimation = new SceneAnimation(imageView1, Constants.ca, 100);
        }
    }

    private Intent mService;
    LottoService.LottoBinder mBinder;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (LottoService.LottoBinder) service;
            boolean stop = mBinder.isStop();
            if (stop) {
                mBinder.startRun();
                mBinder.lunXun();
            }
            String[] listAll = mBinder.getListAll();
            String[] listZM = mBinder.getListZM();
            initSetData(listZM, listAll);
            mBinder.setOnVideoPlayPosition(new LottoService.OnVideoPlayPosition() {
                @Override
                public void sendPosition(int kjdatasize, String zm, String sx) {
                    Log.e("KJ", "sendPosition:" + kjdatasize);
                    TextView zm_view = zm_views.get(kjdatasize);
                    TextView textView = sx_views.get(kjdatasize);
                    View view = views.get(kjdatasize);
                    setvis(view);
                    zm_view.setBackgroundResource(MyUtils.isRBG(zm));
                    zm_view.setText(zm);
                    textView.setText(sx);
                    if (!isMusicStop) {
                        startAnima(view);
                        isMusicID(zm, kjdatasize, sx);
                    }
                    if (kjdatasize == 5) {
                        setvis(views.get(7));
                        startAnima(views.get(7));
                    }
                }

                @Override
                public void sendZT(int zt) {
                    Log.e("KJ:", "" + Lottery.getLottery().toString());
                    if (zt == 1) {

                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
