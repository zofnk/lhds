package com.lh16808.app.lhds.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import com.lh16808.app.lhds.MainActivity;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;

/**
 * http://wap.jizhou56.com:8090/e/extend/json/sz.php?enews=6hds   六合大师设置
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
//        scaleImage(this, (ImageView) findViewById(R.id.rootView), R.drawable.bg_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected void loadData() {

    }


    public void scaleImage(final Activity activity, final ImageView view, final int drawableResId) {

        // 获取屏幕的高宽
        Point outSize = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);

        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(activity.getResources(), drawableResId);

        if (resourceBitmap == null) {
            return;
        }

        // 开始对图片进行拉伸或者缩放

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize.x * 1.0f / resourceBitmap.getWidth());

        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;


                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = view.getMeasuredHeight();


                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;

                try {
                    // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                    Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
                            scaledBitmap.getHeight() - offset * 2);

                    if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                        scaledBitmap.recycle();
                        System.gc();
                    }
                    // 设置图片显示
                    view.setImageDrawable(new BitmapDrawable(activity.getResources(), finallyBitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setImageResource(drawableResId);
                } finally {
                    // 设置图片显示

                }
                return true;
            }
        });
    }
}
