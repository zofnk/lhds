package com.lh16808.app.lhds.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.other.GlideCircleTransform;

/**
 * Created by Administrator on 2016/11/12.
 */

public class ImageLoader {
    /**
     * 网络加载图片，含头像默认图片
     *
     * @param mContext
     * @param url
     * @param view
     */
    public static void LoaderNetHead(Context mContext, String url, ImageView view) {
        Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).placeholder(R.drawable.ico_mine_login_person).error(R.drawable.ico_mine_login_person_error)
                .into(view);
    }
    /**
     * 网络加载图片，含头像默认图片
     *
     * @param mContext
     * @param b
     * @param view
     */
    public static void LoaderNetHead(Context mContext, byte[] b, ImageView view) {
        Glide.with(mContext).load(b).transform(new GlideCircleTransform(mContext)).placeholder(R.drawable.ico_mine_login_person).error(R.drawable.ico_mine_login_person_error)
                .into(view);
    }
    /**
     * 网络加载图片，含默认图片
     *
     * @param mContext
     * @param url
     * @param view
     */
    public static void LoaderNet(Context mContext, String url, ImageView view, int width, int height) {
        Glide.with(mContext).load(url).override(width, height).placeholder(R.drawable.bg_default_no_pic)
                .error(R.drawable.bg_default_no_pic).into(view);
    }

    /**
     * 网络加载图片，含默认图片
     *
     * @param mContext
     * @param url
     * @param view
     */
    public static void LoaderNet(Context mContext, String url, ImageView view) {
        Glide.with(mContext).load(url).placeholder(R.drawable.image_detaila).error(R.drawable.bg_default_no_pic).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(view);
    }

}
