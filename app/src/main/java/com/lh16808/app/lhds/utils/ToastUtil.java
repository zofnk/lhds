package com.lh16808.app.lhds.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * ToastUtil 帮助类
 * 
 * @author chenzhifeng
 * @e-mail seven2729@126.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年10月19日 下午2:19:56
 *
 */
public class ToastUtil {
	private static Toast toast = null;
	public static int LENGTH_LONG = Toast.LENGTH_LONG;
	public static int LENGTH_SHORT = Toast.LENGTH_SHORT;

	// 弹窗信息
	public static void toastShow(Context context, int resId) {
		// if (!context.getString(resId).equals(""))
		if (context != null) {
			if (context.getString(resId) != null) {
				if (toast == null) {
					toast = Toast.makeText(context, context.getString(resId), LENGTH_SHORT);
				} else {
					toast.setText(context.getString(resId));
				}
				toast.show();
			}
		}

	}

	// 弹窗信息
	public static void toastShow(Context context, String msg) {
		if (msg != null) {
			if (toast == null) {
				toast = Toast.makeText(context, msg, LENGTH_SHORT);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
	}

	/**
	 * 普通文本消息提示
	 * 
	 * @param context
	 * @param text
	 */
	public static void toastShow(Context context, CharSequence text) {
		if (context != null) {
			if (toast == null) {
				toast = Toast.makeText(context, text, LENGTH_SHORT);
			} else {
				toast.setText(text);
			}
			toast.show();
		}
	}

	/**
	 * 消息提示
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showLong(Context context, int resId) {
		// 创建一个Toast提示消息
		if (context != null) {
			if (context.getString(resId) == null) {
				toast = Toast.makeText(context, context.getString(resId), LENGTH_LONG);
			} else {
				toast.setText(context.getString(resId));
			}
			toast.show();
		}

	}

	public static void showLong(Context context, String text) {
		if (context != null) {
			if (toast == null) {
				toast = Toast.makeText(context, text, LENGTH_LONG);
			} else {
				toast.setText(text);
			}
			toast.show();
		}
	}

	/**
	 * 带图片消息提示
	 * 
	 * @param context
	 * @param ImageResourceId
	 * @param text
	 * @param duration
	 */
	public static void showImg(Context context, int ImageResourceId, CharSequence text) {
		// 创建一个Toast提示消息
		toast = Toast.makeText(context, text, LENGTH_SHORT);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 获取Toast提示消息里原有的View
		View toastView = toast.getView();
		// 创建一个ImageView
		ImageView img = new ImageView(context);
		img.setImageResource(ImageResourceId);
		// 创建一个LineLayout容器
		LinearLayout ll = new LinearLayout(context);
		// 向LinearLayout中添加ImageView和Toast原有的View
		ll.addView(img);
		ll.addView(toastView);
		// 将LineLayout容器设置为toast的View
		toast.setView(ll);
		// 显示消息
		toast.show();
	}

}
