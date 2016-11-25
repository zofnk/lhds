package com.lh16808.app.lhds.utils;

import android.util.Log;

import com.lh16808.app.lhds.marco.ApiConfig;


/**
 * 打印日志调用
 * 
 * @author zyj
 */
public class AppLog {
	// public static boolean logable = true;

	public static void redLog(String aTag, String aLogInfo) {
		if (ApiConfig.IS_LOG && aLogInfo != null) {
			Log.e(aTag, aLogInfo);
			aLogInfo = null;
			aTag = null;
		} else if (ApiConfig.IS_LOG && aLogInfo == null) {
			Log.e(aTag, "null error");
		}
	}

	public static void greenLog(String aTag, String aLogInfo) {
		if (ApiConfig.IS_LOG && aLogInfo != null) {
			Log.i(aTag, aLogInfo);
			aLogInfo = null;
			aTag = null;
		} else if (ApiConfig.IS_LOG && aLogInfo == null) {
			Log.e(aTag, "null error");
		}
	}

	public static void yellowLog(String aTag, String aLogInfo) {
		if (ApiConfig.IS_LOG && aLogInfo != null) {
			Log.w(aTag, aLogInfo);
			aLogInfo = null;
			aTag = null;
		} else if (ApiConfig.IS_LOG && aLogInfo == null) {
			Log.e(aTag, "null error");
		}
	}

	public static void blackLog(String aTag, String aLogInfo) {
		if (ApiConfig.IS_LOG && aLogInfo != null) {
			Log.d(aTag, aLogInfo);
			aLogInfo = null;
			aTag = null;
		} else if (ApiConfig.IS_LOG && aLogInfo == null) {
			Log.e(aTag, "null error");
		}
	}

	public static void debug(String aTag, String aLogInfo) {
		if (ApiConfig.IS_LOG && aLogInfo != null) {
			Log.e(aTag, aLogInfo);
			aLogInfo = null;
			aTag = null;
		} else if (ApiConfig.IS_LOG && aLogInfo == null) {
			Log.e(aTag, "null error");
		}

	}

}
