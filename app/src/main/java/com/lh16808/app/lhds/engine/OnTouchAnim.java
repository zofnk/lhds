package com.lh16808.app.lhds.engine;

/**
 * Created by Administrator on 2016/11/18.
 */

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zjj on 2016/3/7.
 */
public class OnTouchAnim implements View.OnTouchListener, View.OnFocusChangeListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                toSmallAnimation(v);
                break;
            case MotionEvent.ACTION_UP:
                toNormalAnimation(v);
                break;
        }
        return false;
    }

    private void toBigAnimation(final View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "big", view.getScaleX(), 1.1f)
                .setDuration(100);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    private void toNormalAnimation(final View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "normal", view.getScaleX(), 1.0f)
                .setDuration(100);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    private void toSmallAnimation(final View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "small", view.getScaleX(), 0.95f)
                .setDuration(100);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
            toNormalAnimation(v);
    }
}