package com.lh16808.app.lhds.other;

import android.widget.ImageView;

public class SceneAnimation {
    private ImageView mImageView;
    private int[] mFrameRess;
    private int[] mFrameRessX;
    private int[] mDurations;
    private int mDuration;
    private int mLastFrameNo;
    public static boolean ispaly;
    private Runnable action;

    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int[] pDurations) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDurations = pDurations;
        mLastFrameNo = pFrameRess.length - 1;
        mImageView.setBackgroundResource(mFrameRess[0]);
        play(1);
        ispaly = true;
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;

        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
        ispaly = true;
    }

    public void setFrameRess(int[] pFrameRess) {
        mFrameRessX = pFrameRess;
        ispaly = true;
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration, long pBreakDelay) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;
        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
        ispaly = true;
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
                    playConstant(0);
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
