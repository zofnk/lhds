package com.lh16808.app.lhds.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lh16808.app.lhds.R;


/**
 * Created by admin on 2016/9/22.
 */
public class LuckyPanView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean isRunning;

    private Thread t;

    /**
     * 滚动的速度
     */
    private double mSpeed;
    /**
     * 是否点击了停止
     */
    private boolean isShouldEnd;

    /**
     * 圆的半径
     */
    private int radius;

    /**
     * 圆的中心点
     *
     * @param context
     */
    private int center;

    /**
     * paddding值(以左padding为准）
     *
     * @param context
     */
    private int padding;

    RectF mRectF;

    private Paint mArcPaint;
    private Paint mTxtPaint;
    private Bitmap mBg;

    private int mItemCounts = 12;

    private int[] mArcColors = new int[]{0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300};
    /**
     * 抽奖的文字
     */
//    private String[] mStrs = new String[]{"单反相机", "IPAD", "恭喜发财", "IPHONE",
//            "妹子一只", "恭喜发财"};
    private String[] mStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    /**
     * 与文字对应的图片
     */
//    private int[] mImgs = new int[]{R.drawable.danfan, R.drawable.ipad,
//            R.drawable.f040, R.drawable.iphone, R.drawable.meizi,
//            R.drawable.f040, R.drawable.iphone, R.drawable.f040,  R.drawable.meizi,
//            R.drawable.f040};

//    private ArrayList<Bitmap> mIcons;
    public LuckyPanView(Context context) {
        super(context);
    }

    public LuckyPanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);

        //设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常亮
        this.setKeepScreenOn(true);

        Log.e("TAG", "LuckyPanView");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 初始化绘制圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        // 初始化绘制文字的画笔
        mTxtPaint = new Paint();
        mTxtPaint.setColor(Color.WHITE);
        mTxtPaint.setTextSize(60);

        mBg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        mRectF = new RectF(getPaddingLeft() + 50, getPaddingLeft() + 50, getPaddingLeft() + radius * 2 - 50, getPaddingLeft() + radius * 2 - 50);

//        mIcons = new ArrayList<>();
//        for (int i = 0; i < mItemCounts; i++) {
//            mIcons.add(BitmapFactory.decodeResource(getResources(), mImgs[i]));
//        }
        isRunning = true;
        t = new Thread(this);
        t.start();
        Log.e("TAG", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("TAG", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("TAG", "surfaceDestroyed");
    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                drawBg();
                drawArc();
            }
        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private float mStartAngle = 0;

    private void drawArc() {
        float tmpAngle = mStartAngle;
        float sweepAngle = 360 / mItemCounts;
        for (int i = 0; i < mItemCounts; i++) {
            mArcPaint.setColor(mArcColors[i]);
            mCanvas.drawArc(mRectF, tmpAngle, sweepAngle, true, mArcPaint);
            drawTxt(tmpAngle, sweepAngle, mStrs[i]);
//            drawIcon(tmpAngle, i);
            tmpAngle += sweepAngle;
        }


        // 如果mSpeed不等于0，则相当于在滚动
        mStartAngle += mSpeed;

        // 点击停止时，设置mSpeed为递减，为0值转盘停止
        if (isShouldEnd) {
            mSpeed -= 1;
        }
        if (mSpeed <= 0) {
            mSpeed = 0;
            isShouldEnd = false;
        }
        // 根据当前旋转的mStartAngle计算当前滚动到的区域
        calInExactArea(mStartAngle);

    }

    public void calInExactArea(float startAngle) {
        float rotate = startAngle + 90;
        rotate %= 360.0;
        for (int i = 0; i < mItemCounts; i++) {
            float from = 360 - (i + 1) * (360 / mItemCounts);
            float to = from + 360 - (i) * (360 / mItemCounts);

            if ((rotate > from) && (rotate < to)) {
                Log.d("TAG", mStrs[i]);
                return;
            }
        }
    }

    private void drawTxt(float tmpAngle, float sweepAngle, String txt) {

        Path path = new Path();
        path.addArc(mRectF, tmpAngle, sweepAngle);

        float hOffset = (float) (radius * 2 * Math.PI / mItemCounts / 2 - mTxtPaint.measureText(txt) / 2);// 水平偏移
        float vOffset = radius / 6;// 垂直偏移
        mCanvas.drawTextOnPath(txt, path, hOffset, vOffset, mTxtPaint);
    }

    private void drawBg() {
//        mCanvas.drawArc(mRectF, 0, 360, true, mArcPaint);

        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(mBg, null, new Rect(padding / 2,
                padding / 2, getMeasuredWidth() - padding / 2,
                getMeasuredWidth() - padding / 2), null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());

        radius = width / 2 - getPaddingLeft() - getPaddingRight();
        center = radius;
        padding = getPaddingLeft();
        setMeasuredDimension(width, width);

        Log.e("TAG", "onMeasure");
    }

    /**
     * 点击开始旋转
     *
     * @param luckyIndex
     */
    public void luckyStart(int luckyIndex) {
        // 每项角度大小
        float angle = (float) (360 / mItemCounts);
        // 中奖角度范围（因为指针向上，所以水平第一项旋转到指针指向，需要旋转210-270；）
        float from = 270 - (luckyIndex + 1) * angle;
        float to = from + angle;
        // 停下来时旋转的距离
        float targetFrom = 4 * 360 + from;
        /**
         * <pre>
         *  (v1 + 0) * (v1+1) / 2 = target ;
         *  v1*v1 + v1 - 2target = 0 ;
         *  v1=-1+(1*1 + 8 *1 * target)/2;
         * </pre>
         */
        float v1 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetFrom) - 1) / 2;
        float targetTo = 4 * 360 + to;
        float v2 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetTo) - 1) / 2;

        mSpeed = (float) (v1 + Math.random() * (v2 - v1));
        isShouldEnd = false;
    }

    public void luckyEnd() {
        mStartAngle = 0;
        isShouldEnd = true;
    }

}
