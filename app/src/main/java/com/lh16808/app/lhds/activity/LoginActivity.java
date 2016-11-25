package com.lh16808.app.lhds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.base.listener.OnLoginLinstener;
import com.lh16808.app.lhds.fragment.FindPasswordFragment;
import com.lh16808.app.lhds.fragment.LoginFragment;
import com.lh16808.app.lhds.fragment.RegisterFragment;
import com.lh16808.app.lhds.widget.MySpeedScroller;
import com.lh16808.app.lhds.widget.MyViewPager;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements OnLoginLinstener {
    private Drawable drawable;
    private ImageView ivBg;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyViewPager viewPager;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ivBg = (ImageView) findViewById(R.id.iv_bg);
        drawable = ivBg.getDrawable();
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
                if (!(sdkVersion < 19)) {
                    ivBg.startAnimation(animation);
                }
            }
        }, 200);
        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setListener(this);
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setListener(this);
        FindPasswordFragment findPasswordFragment = new FindPasswordFragment();
        findPasswordFragment.setListener(this);
        fragments.add(registerFragment);
        fragments.add(loginFragment);
        fragments.add(findPasswordFragment);

        MySpeedScroller scroller = new MySpeedScroller(this, new OvershootInterpolator(1f));
        scroller.setmDuration(800);

        viewPager.setAdapter(new ForLoginFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        viewPager.setSpeed(scroller);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void setCurrentUI(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void setViewPagerGesture(boolean gesture) {
        if (viewPager != null) {
            viewPager.setSupportGesture(gesture);
        }
    }

    @Override
    public void registerSuccessCallback(String phone, String password) {

    }

    class ForLoginFragmentPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> list;

        public ForLoginFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (!bitmap.isRecycled()) {
//                bitmap.recycle();
                bitmap = null;
            }
        }
        super.onDestroy();
    }
}
