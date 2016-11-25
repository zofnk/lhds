package com.lh16808.app.lhds.other;

import android.app.Activity;
import android.widget.ImageView;

import com.lh16808.app.lhds.R;


public class SceneAnimation2 {
    private ImageView mImageView;
    private int[] mFrameRess;
    private int[] mFrameRessX;
    private int[] mDurations;
    private int mDuration;
    private Activity mActivity;
    private int mLastFrameNo;
    private Runnable action;

    public SceneAnimation2(ImageView pImageView, int[] pFrameRess, int[] pDurations) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDurations = pDurations;
        mLastFrameNo = pFrameRess.length - 1;
        mImageView.setBackgroundResource(mFrameRess[0]);
        play(1);
    }

    public SceneAnimation2(ImageView pImageView, int[] pFrameRess, int pDuration, Activity mActivity) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        this.mActivity = mActivity;
        mLastFrameNo = pFrameRess.length - 1;
        SceneAnimation.ispaly = false;
        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
    }

    public void setFrameRess(int[] pFrameRess) {
        mFrameRessX = pFrameRess;
    }

    public SceneAnimation2(ImageView pImageView, int[] pFrameRess, int pDuration, long pBreakDelay) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;
        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
    }

    private void play(final int pFrameNo) {
        action = new Runnable() {
            public void run() {
                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                if (pFrameNo == mLastFrameNo)
                    play(0);
                else
                    play(pFrameNo + 1);
            }
        };
        mImageView.postDelayed(action, mDurations[pFrameNo]);
    }

    private void playConstant(final int pFrameNo) {
        action = new Runnable() {

            public void run() {
                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                if (pFrameNo == mLastFrameNo) {
                    if (mFrameRessX != null) {
                        mFrameRess = mFrameRessX;
                        mLastFrameNo = mFrameRess.length - 1;
                    }
                    mImageView.setBackgroundResource(R.drawable.kj_sa1);
                    mActivity.finish();
                    // playConstant(0);
                } else {
                    int pFrameNo2 = pFrameNo + 1;
                    playConstant(pFrameNo2);
                }
            }
        };
        mImageView.postDelayed(action, mDuration);
    }

    public void reomve() {
        if (action != null) {
            mImageView.removeCallbacks(action);
        }
    }
}
