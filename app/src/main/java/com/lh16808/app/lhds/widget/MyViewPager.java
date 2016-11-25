package com.lh16808.app.lhds.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/18.
 */

public class MyViewPager  extends ViewPager {

    private boolean supportGesture = false;

    public boolean isSupportGesture() {
        return supportGesture;
    }

    public void setSupportGesture(boolean supportGesture) {
        this.supportGesture = supportGesture;
    }

    private MySpeedScroller mScroller = null;

    public void setSpeed(MySpeedScroller mScroller) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            mField.set(this, mScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (supportGesture) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return supportGesture;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
