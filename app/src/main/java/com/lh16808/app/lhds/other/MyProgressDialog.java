package com.lh16808.app.lhds.other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lh16808.app.lhds.R;


public class MyProgressDialog extends Thread {

	private static ProgressDialog progressDialog = null;

	private static Activity mContext;

	public static void dialogShow(Activity context) {
		mContext = context;
		View progress_dialog = View.inflate(context, R.layout.dialog_loading, null);
		((TextView) progress_dialog.findViewById(R.id.loadtxt)).setText("加载中...");
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context, R.style.DialogThemeNoTitle1);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setIndeterminate(false);
		}
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		if (progressDialog != null && progress_dialog != null) {
			progressDialog.setContentView(progress_dialog);
			progressDialog.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					progressDialog = null;
				}
			});
		}
		new myThread().start();
	}

	public static void dialogHide() {
		if (progressDialog != null)
			progressDialog.dismiss();
		progressDialog = null;
	}

	static class myThread extends Thread {
		@Override
		public void run() {
			for (int i = 0; i <= 15; i++) {
				if (progressDialog == null || !progressDialog.isShowing()) {
					break;
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (progressDialog != null) {
				if (mContext != null && progressDialog.isShowing()) {
					mContext.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
						}
					});
				}
				dialogHide();
			}

		}
	}

}
