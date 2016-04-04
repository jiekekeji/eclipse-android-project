package com.jk.earn.net.load;

import java.lang.reflect.Method;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

public class DownloadManagerPro {

	public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");

	/** represents downloaded file above api 11 **/

	public static final String COLUMN_LOCAL_FILENAME = "local_filename";

	/** represents downloaded file below api 11 **/
	public static final String COLUMN_LOCAL_URI = "local_uri";

	public static final String METHOD_NAME_PAUSE_DOWNLOAD = "pauseDownload";
	public static final String METHOD_NAME_RESUME_DOWNLOAD = "resumeDownload";

	private static boolean isInitPauseDownload = false;
	private static boolean isInitResumeDownload = false;

	private static Method pauseDownload = null;
	private static Method resumeDownload = null;

	private DownloadManager downloadManager;

	public DownloadManagerPro(DownloadManager downloadManager) {
		this.downloadManager = downloadManager;
	}

	/**
	 * 根据下载任务id获取下载的状态
	 * 
	 * @param downloadId
	 * 
	 * @return
	 */
	public int getStatusById(long downloadId) {
		return getInt(downloadId, DownloadManager.COLUMN_STATUS);
	}

	/**
	 * 获取已下载的，总的文件大小
	 * 
	 * @param downloadId
	 * 
	 * 
	 * @return int型数组。[0];当前已下载的bytes。[1];总的文件大小
	 */
	public int[] getDownloadBytes(long downloadId) {
		int[] bytesAndStatus = getBytesAndStatus(downloadId);
		return new int[] { bytesAndStatus[0], bytesAndStatus[1] };
	}

	/**
	 * 根据下载id获取文件的下载状态
	 * 
	 * @param downloadId
	 *            下载id
	 * 
	 * @return int[] 数组。 [0]:表示当前已下载的bytes, [1]:文件总的大小 ,[2]:下载的状态.
	 */
	public int[] getBytesAndStatus(long downloadId) {
		int[] bytesAndStatus = new int[] { -1, -1, 0 };
		DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
		Cursor cursor = null;
		try {
			cursor = downloadManager.query(query);
			if (cursor != null && cursor.moveToFirst()) {
				bytesAndStatus[0] = cursor.getInt(cursor
						.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
				bytesAndStatus[1] = cursor
						.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
				bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return bytesAndStatus;
	}

	/**
	 * 根据id值暂停多个正在下载的任务
	 * 
	 * @param ids
	 * 
	 * @return 成功暂停，返回该下载任务的id，否则返回-1.
	 */
	public int pauseDownload(long... ids) {
		initPauseMethod();
		if (pauseDownload == null) {
			return -1;
		}

		try {
			return ((Integer) pauseDownload.invoke(downloadManager, ids)).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 恢复已暂停的下载任务
	 * 
	 * @param ids
	 * 
	 * @return 成功恢复下载返回下载任务id,否则返回-1
	 */
	public int resumeDownload(long... ids) {
		initResumeMethod();
		if (resumeDownload == null) {
			return -1;
		}

		try {
			return ((Integer) resumeDownload.invoke(downloadManager, ids)).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * whether exist pauseDownload and resumeDownload method in
	 * {@link DownloadManager}
	 * 
	 * @return
	 */
	public static boolean isExistPauseAndResumeMethod() {
		initPauseMethod();
		initResumeMethod();
		return pauseDownload != null && resumeDownload != null;
	}

	private static void initPauseMethod() {
		if (isInitPauseDownload) {
			return;
		}

		isInitPauseDownload = true;
		try {
			pauseDownload = DownloadManager.class.getMethod(METHOD_NAME_PAUSE_DOWNLOAD, long[].class);
		} catch (Exception e) {
			// accept all exception
			e.printStackTrace();
		}
	}

	private static void initResumeMethod() {
		if (isInitResumeDownload) {
			return;
		}

		isInitResumeDownload = true;
		try {
			resumeDownload = DownloadManager.class.getMethod(METHOD_NAME_RESUME_DOWNLOAD, long[].class);
		} catch (Exception e) {
			// accept all exception
			e.printStackTrace();
		}
	}

	/**
	 * get download file name
	 * 
	 * @param downloadId
	 * @return
	 */
	public String getFileName(long downloadId) {
		return getString(downloadId, (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? COLUMN_LOCAL_URI
				: COLUMN_LOCAL_FILENAME));
	}

	/**
	 * get download uri
	 * 
	 * @param downloadId
	 * @return
	 */
	public String getUri(long downloadId) {
		return getString(downloadId, DownloadManager.COLUMN_URI);
	}

	/**
	 * get failed code or paused reason
	 * 
	 * @param downloadId
	 * @return <ul>
	 *         <li>if status of downloadId is
	 *         {@link DownloadManager#STATUS_PAUSED}, return
	 *         {@link #getPausedReason(long)}</li>
	 *         <li>if status of downloadId is
	 *         {@link DownloadManager#STATUS_FAILED}, return
	 *         {@link #getErrorCode(long)}</li>
	 *         <li>if status of downloadId is neither
	 *         {@link DownloadManager#STATUS_PAUSED} nor
	 *         {@link DownloadManager#STATUS_FAILED}, return 0</li>
	 *         </ul>
	 */
	public int getReason(long downloadId) {
		return getInt(downloadId, DownloadManager.COLUMN_REASON);
	}

	/**
	 * get paused reason
	 * 
	 * @param downloadId
	 * @return <ul>
	 *         <li>if status of downloadId is
	 *         {@link DownloadManager#STATUS_PAUSED}, return one of
	 *         {@link DownloadManager#PAUSED_WAITING_TO_RETRY}<br/>
	 *         {@link DownloadManager#PAUSED_WAITING_FOR_NETWORK}<br/>
	 *         {@link DownloadManager#PAUSED_QUEUED_FOR_WIFI}<br/>
	 *         {@link DownloadManager#PAUSED_UNKNOWN}</li>
	 *         <li>else return {@link DownloadManager#PAUSED_UNKNOWN}</li>
	 *         </ul>
	 */
	public int getPausedReason(long downloadId) {
		return getInt(downloadId, DownloadManager.COLUMN_REASON);
	}

	/**
	 * get failed error code
	 * 
	 * @param downloadId
	 * @return one of {@link DownloadManager#ERROR_*}
	 */
	public int getErrorCode(long downloadId) {
		return getInt(downloadId, DownloadManager.COLUMN_REASON);
	}

	public static class RequestPro extends DownloadManager.Request {

		public static final String METHOD_NAME_SET_NOTI_CLASS = "setNotiClass";
		public static final String METHOD_NAME_SET_NOTI_EXTRAS = "setNotiExtras";

		private static boolean isInitNotiClass = false;
		private static boolean isInitNotiExtras = false;

		private static Method setNotiClass = null;
		private static Method setNotiExtras = null;

		/**
		 * @param uri
		 *            the HTTP URI to download.
		 */
		public RequestPro(Uri uri) {
			super(uri);
		}

		/**
		 * set noti class, only init once
		 * 
		 * @param className
		 *            full class name
		 */
		public void setNotiClass(String className) {
			synchronized (this) {

				if (!isInitNotiClass) {
					isInitNotiClass = true;
					try {
						setNotiClass = Request.class.getMethod(METHOD_NAME_SET_NOTI_CLASS, CharSequence.class);
					} catch (Exception e) {
						// accept all exception
						e.printStackTrace();
					}
				}
			}

			if (setNotiClass != null) {
				try {
					setNotiClass.invoke(this, className);
				} catch (Exception e) {
					/**
					 * accept all exception, include ClassNotFoundException,
					 * NoSuchMethodException, InvocationTargetException,
					 * NullPointException
					 */
					e.printStackTrace();
				}
			}
		}

		/**
		 * set noti extras, only init once
		 * 
		 * @param extras
		 */
		public void setNotiExtras(String extras) {
			synchronized (this) {

				if (!isInitNotiExtras) {
					isInitNotiExtras = true;
					try {
						setNotiExtras = Request.class.getMethod(METHOD_NAME_SET_NOTI_EXTRAS, CharSequence.class);
					} catch (Exception e) {
						// accept all exception
						e.printStackTrace();
					}
				}
			}

			if (setNotiExtras != null) {
				try {
					setNotiExtras.invoke(this, extras);
				} catch (Exception e) {
					/**
					 * accept all exception, include ClassNotFoundException,
					 * NoSuchMethodException, InvocationTargetException,
					 * NullPointException
					 */
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * get string column
	 * 
	 * @param downloadId
	 * @param columnName
	 * @return
	 */
	private String getString(long downloadId, String columnName) {
		DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
		String result = null;
		Cursor c = null;
		try {
			c = downloadManager.query(query);
			if (c != null && c.moveToFirst()) {
				result = c.getString(c.getColumnIndex(columnName));
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	/**
	 * get int column
	 * 
	 * @param downloadId
	 * @param columnName
	 * @return
	 */
	private int getInt(long downloadId, String columnName) {
		DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
		int result = -1;
		Cursor c = null;
		try {
			c = downloadManager.query(query);
			if (c != null && c.moveToFirst()) {
				result = c.getInt(c.getColumnIndex(columnName));
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}
}
