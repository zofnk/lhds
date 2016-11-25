package com.lh16808.app.lhds.utils;

import android.os.Environment;

import java.io.File;

/** 路径信息 */
public final class PathInfo
{
	/** 根 */
	public static String SDCardPath = "JiaYouUserSide";
	/** 图片 */
	public static String ImageSubPath = "Image";
	/** 临时 */
	public static String TempPath = "Temp";

	/** 扩展存储目录(SD卡) */
	public static String SDPATH = Environment.getExternalStorageDirectory().getPath() + File.separator;

	/**根/ */
	public static String getRootPath()
	{
		return SDCardPath + "/" ;
	}


	/**根/Image/ */
	public static String getImageSubPath()
	{
		return getRootPath() + ImageSubPath + "/";
	}

	/**根/Temp/ */
	public static String getTempPath()
	{
		return getRootPath() + TempPath + "/";
	}

	/** 在登陆成功之后创建客户端存放资料的所有文件夹 */
	public static void createAllPath()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				File file = new File(SDPATH + PathInfo.getImageSubPath());
				if (!file.exists())
					file.mkdirs();
				file = new File(SDPATH + PathInfo.getTempPath());
				if (!file.exists())
					file.mkdirs();
			}
		}).start();
	}
}
