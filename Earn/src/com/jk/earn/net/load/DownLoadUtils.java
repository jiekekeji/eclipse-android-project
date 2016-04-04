package com.jk.earn.net.load;

import java.io.File;
import java.text.DecimalFormat;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class DownLoadUtils {

	private final static String TAG = DownLoadUtils.class.getSimpleName();

	// {后缀名，MIME类型}
	private final static String[][] MIME_MapTable = { { ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" }, { ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
			{ ".c", "text/plain" }, { ".class", "application/octet-stream" }, { ".conf", "text/plain" },
			{ ".cpp", "text/plain" }, { ".doc", "application/msword" },
			{ ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" }, { ".gif", "image/gif" }, { ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".htm", "text/html" }, { ".html", "text/html" },
			{ ".jar", "application/java-archive" }, { ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
			{ ".png", "image/png" }, { ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" },
			{ ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/x-zip-compressed" },
			{ "", "*/*" } };

	/**
	 * 保存下载任务id和文件名的文件
	 */
	private static final String SHARED_PREFERENCES_NAME = "shared_preferences_name";

	/**
	 * 下载文件的保存文件夹
	 */
	private static final String DOWNLOAD_DIR = "Demo";

	private static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");

	public static final int MB_2_BYTE = 1024 * 1024;
	public static final int KB_2_BYTE = 1024;

	/**
	 * 将字节数转为kb,获取mb
	 * 
	 * @param size
	 * 
	 * @return
	 */
	public static CharSequence getAppSize(long size) {
		if (size <= 0) {
			return "0M";
		}
		if (size >= MB_2_BYTE) {
			return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double) size / MB_2_BYTE)).append("M");
		} else if (size >= KB_2_BYTE) {
			return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double) size / KB_2_BYTE)).append("K");
		} else {
			return size + "B";
		}
	}

	/**
	 * 计算百分比
	 * 
	 * @param progress
	 *            当前大小
	 * @param max
	 *            总大小
	 * @return 当前大小占总大小的比重
	 */
	public static String getNotiPercent(long progress, long max) {
		int rate = 0;
		if (progress <= 0 || max <= 0) {
			rate = 0;
		} else if (progress > max) {
			rate = 100;
		} else {
			rate = (int) ((double) progress / max * 100);
		}
		return new StringBuilder(16).append(rate).append("%").toString();
	}

	/***
	 * 开始下载
	 * 
	 * @param url
	 * 
	 * @return 标识当前下载的唯一id
	 */
	public static long startDownLoad(String url, DownloadManager downloadManager, Context context) {

		Uri uri = Uri.parse(url);
		DownloadManager.Request request = new Request(uri);

		String fileName = url.substring(url.lastIndexOf("/") + 1);

		// 设置应用私有保存路径
		// request.setDestinationInExternalFilesDir(context,
		// Environment.DIRECTORY_DOWNLOADS, fileName);

		// 设置共有保存路径
		request.setDestinationInExternalPublicDir(DOWNLOAD_DIR, fileName);
		// 通知栏一直显示下载信息
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		// 表示允许MediaScanner扫描到这个文件，默认不允许。
		request.allowScanningByMediaScanner();
		// // 设置下载中通知栏提示的标题
		// request.setTitle("通知栏提示标题");
		// // 设置下载中通知栏提示的介绍
		// request.setDescription("通知栏提示的介绍");
		/**
		 * 表示下载允许的网络类型，默认在任何网络下都允许下载。有NETWORK_MOBILE、NETWORK_WIFI、
		 * NETWORK_BLUETOOTH三种及其组合可供选择。如果只允许wifi下载，而当前网络为3g，则下载会等待。
		 */
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		// 移动网络情况下是否允许漫游
		request.setAllowedOverRoaming(true);
		/**
		 * 设置下载文件的mineType。因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，
		 * 所以我们可以利用这个属性。比如上面设置了mimeType为application/cn.trinea.download.file，
		 * 我们可以同时设置某个Activity的intent
		 * -filter为application/cn.trinea.download.file，用于响应点击的打开文件。
		 * request1.setMimeType("application/cn.trinea.download.file");
		 */

		/**
		 * 添加请求下载的网络链接的http头，比如User-Agent，gzip压缩等
		 * request1.addRequestHeader(String header, String value);
		 */

		long id = downloadManager.enqueue(request);
		putFileNameById(context, String.valueOf(id), fileName);

		return id;
	}

	/**
	 * 保存下载的文件名
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	public static void putFileNameById(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 根据下载任务id获取下载任务名称
	 * 
	 * @param context
	 * @param key
	 * 
	 * @return 存在返回文件名,否则返回null
	 */
	public static String getFileNameById(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, null);
	}

	/***
	 * 取消下载任务
	 * 
	 * @param ids
	 * 
	 * @return 取消的条数
	 */
	public static int cancelDownLoad(DownloadManager downloadManager, long... ids) {
		return downloadManager.remove(ids);
	}

	/**
	 * 判断下载的状态
	 * 
	 * @param downloadManagerStatus
	 * @return
	 */
	public static boolean isDownloading(int downloadManagerStatus) {
		return downloadManagerStatus == DownloadManager.STATUS_RUNNING
				|| downloadManagerStatus == DownloadManager.STATUS_PAUSED
				|| downloadManagerStatus == DownloadManager.STATUS_PENDING;
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	/***
	 * 打开已下载的文件
	 * 
	 * @param context
	 * 
	 * @param cursor
	 */
	public static void openDownload(Context context, String fileName) {

		File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + DOWNLOAD_DIR, fileName);
		Log.i(TAG, "文件大小" + file.length());
		String mimetype = getMIMEType(file);

		Intent intent = new Intent(Intent.ACTION_VIEW);

		Uri uri = Uri.fromFile(file);
		Log.i(TAG, "文件路径" + file.getAbsolutePath());
		intent.setDataAndType(uri, mimetype);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		try {
			context.startActivity(intent);
		} catch (ActivityNotFoundException ex) {
			Log.i(TAG, "无法打开文件");
		}
	}

	/**
	 * 根据下载任务id打开已下载的文件
	 * 
	 * @param id
	 */
	public static void openDownloadById(Context context, String id) {
		openDownload(context, getFileNameById(context, id));
	}
}
