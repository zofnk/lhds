package com.lh16808.app.lhds.widget.imagelook.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.marco.ApiConfig;
import com.lh16808.app.lhds.widget.imagelook.PhotoViewAttacher;

import java.io.File;


/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;
    int type;
    RelativeLayout rl;
    TextView tv;

    public static ImageDetailFragment newInstance(String imageUrl, int type, RelativeLayout rl, TextView tv) {

        final ImageDetailFragment f = new ImageDetailFragment();

        f.rl = rl;
        f.tv = tv;
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putInt("type", type);
        f.setArguments(args);

        return f;
    }

    public static ImageDetailFragment newInstance(String imageUrl, int type, TextView tv) {

        final ImageDetailFragment f = new ImageDetailFragment();

        f.tv = tv;
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putInt("type", type);
        f.setArguments(args);

        return f;
    }

    public static ImageDetailFragment newInstance(String imageUrl, int type) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putInt("type", type);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        type = getArguments() != null ? getArguments().getInt("type") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.zorasun_image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);

        // 对图片监听
        // mAttacher.setOnPhotoTapListener(new OnPhotoTapListener()
        // {
        //
        // @Override
        // public void onPhotoTap(View arg0, float arg1, float arg2)
        // {
        // getActivity().finish();
        // }
        // });

        // 对控件监听
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

            @Override
            public void onViewTap(View view, float x, float y) {
                getActivity().finish();
            }
        });
        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!mImageUrl.startsWith("http://")) {
            // mImageUrl = "sdcard://" + mImageUrl;

            Glide.with(getActivity()).load(new File(mImageUrl)).listener(new RequestListener<File, GlideDrawable>() {

                @Override
                public boolean onException(Exception arg0, File arg1, Target<GlideDrawable> arg2, boolean arg3) {
                    System.out.println("onException");
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable arg0, File arg1, Target<GlideDrawable> arg2, boolean arg3,
                                               boolean arg4) {
                    System.out.println("onResourceReady");
                    mAttacher.update();
                    return false;
                }
            }).placeholder(R.drawable.bg_default_no_pic).error(R.drawable.bg_default_no_pic).into(mImageView);

        } else {
            mImageUrl = ApiConfig.getImageUrl(mImageUrl);
//			ImageLoader.LoaderNet(getActivity(), mImageUrl, mImageView);
            Glide.with(getActivity()).load(mImageUrl).placeholder(R.drawable.image_detaila).error(R.drawable.bg_default_no_pic).into(new GlideDrawableImageViewTarget(mImageView) {
                @Override
                public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                    super.onResourceReady(drawable, anim);
                    //在这里添加一些图片加载完成的操作
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                }
            });
        }

        // AsyncImageLoader.setAsynImages(mImageView, mImageUrl, new
        // ImageLoadingListener() {
        //
        // @Override
        // public void onLoadingStarted(String imageUri, View view) {
        // progressBar.setVisibility(View.VISIBLE);
        //
        // }
        //
        // @Override
        // public void onLoadingFailed(String imageUri, View view, FailReason
        // failReason) {
        // String message = null;
        // switch (failReason.getType()) {
        // case IO_ERROR:
        // message = "下载错误";
        // break;
        // case DECODING_ERROR:
        // message = "图片无法显示";
        // break;
        // case NETWORK_DENIED:
        // message = "网络有问题，无法下载";
        // break;
        // case OUT_OF_MEMORY:
        // message = "图片太大无法显示";
        // break;
        // case UNKNOWN:
        // message = "未知的错误";
        // break;
        // }
        // ToastUtil.toastShow(getActivity(), message);
        // progressBar.setVisibility(View.GONE);
        //
        // }
        //
        // @Override
        // public void onLoadingComplete(String imageUri, View view, Bitmap
        // loadedImage) {
        // progressBar.setVisibility(View.GONE);
        // mAttacher.update();
        // }
        //
        // @Override
        // public void onLoadingCancelled(String arg0, View arg1) {
        //
        // ToastUtil.toastShow(getActivity(), "取消加载");
        // }
        // });

        // if (mImageUrl.startsWith("upload"))
        // {
        //
        // ImageLoader.getInstance().displayImage(ApiConfig.getImageUrl(mImageUrl),
        // mImageView,
        // new SimpleImageLoadingListener()
        // {
        // @Override
        // public void onLoadingStarted(String imageUri, View view)
        // {
        // progressBar.setVisibility(View.VISIBLE);
        // }
        //
        // @Override
        // public void onLoadingFailed(String imageUri, View view, FailReason
        // failReason)
        // {
        // String message = null;
        // switch (failReason.getType())
        // {
        // case IO_ERROR:
        // message = "下载错误";
        // break;
        // case DECODING_ERROR:
        // message = "图片无法显示";
        // break;
        // case NETWORK_DENIED:
        // message = "网络有问题，无法下载";
        // break;
        // case OUT_OF_MEMORY:
        // message = "图片太大无法显示";
        // break;
        // case UNKNOWN:
        // message = "未知的错误";
        // break;
        // }
        // ToastUtils.toastShow(getActivity(), message);
        // progressBar.setVisibility(View.GONE);
        // }
        //
        // @Override
        // public void onLoadingComplete(String imageUri, View view, Bitmap
        // loadedImage)
        // {
        // progressBar.setVisibility(View.GONE);
        // mAttacher.update();
        // }
        // });
        //
        // }
        // else if (mImageUrl.startsWith("http://"))
        // {
        // ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new
        // SimpleImageLoadingListener()
        // {
        // @Override
        // public void onLoadingStarted(String imageUri, View view)
        // {
        // progressBar.setVisibility(View.VISIBLE);
        // }
        //
        // @Override
        // public void onLoadingFailed(String imageUri, View view, FailReason
        // failReason)
        // {
        // String message = null;
        // switch (failReason.getType())
        // {
        // case IO_ERROR:
        // message = "下载错误";
        // break;
        // case DECODING_ERROR:
        // message = "图片无法显示";
        // break;
        // case NETWORK_DENIED:
        // message = "网络有问题，无法下载";
        // break;
        // case OUT_OF_MEMORY:
        // message = "图片太大无法显示";
        // break;
        // case UNKNOWN:
        // message = "未知的错误";
        // break;
        // }
        // ToastUtils.toastShow(getActivity(), message);
        // progressBar.setVisibility(View.GONE);
        // }
        //
        // @Override
        // public void onLoadingComplete(String imageUri, View view, Bitmap
        // loadedImage)
        // {
        // progressBar.setVisibility(View.GONE);
        // mAttacher.update();
        // }
        // });
        //
        // }
        // else
        // {
        // AsyncImageLoader.setAsynAvatarImages(mImageView, "sdcard://" +
        // mImageUrl);
        // }
    }
}
