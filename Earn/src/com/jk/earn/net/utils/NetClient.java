package com.jk.earn.net.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class NetClient {

	private static NetClient netUtils = null;

	private static OkHttpClient okClient = null;

	private NetClient() {
		okClient = new OkHttpClient();
		okClient.setConnectTimeout(180, TimeUnit.SECONDS);
	}

	/**
	 * 实例化NetUtils
	 * 
	 * @return
	 */
	public static NetClient getInstance() {
		if (netUtils == null) {
			netUtils = new NetClient();
		}
		return netUtils;
	}

	/**
	 * get方式发请求，异步返回结果
	 * 
	 * @param url
	 *            完整的请求地址
	 * @param callback
	 *            请求结果的回调
	 */
	public void getFromNetwork(String url, Callback callback, String tag) {
		Request request = new Request.Builder().url(url).tag(tag).build();
		okClient.newCall(request).enqueue(callback);
	}

	/**
	 * post以挂参数的方式发送请求，异步返回结果
	 * 
	 * @param url
	 * @param map
	 * @param callback
	 * @param tag
	 */
	public void getFromNetwork(String url, Map<String, String> map, Callback callback, String tag) {
		Request request = null;

		FormEncodingBuilder builder = new FormEncodingBuilder();
		/**
		 * 设置参数
		 */
		for (Map.Entry<String, String> entry : map.entrySet()) {
			builder.add(entry.getKey(), entry.getValue());
		}

		RequestBody body = builder.build();

		/**
		 * 实例化请求
		 */
		request = new Request.Builder().url(url).post(body).tag(tag).build();

		okClient.newCall(request).enqueue(callback);

	}

	/**
	 * post以流的方式发送xml格式的字符串
	 * 
	 * @param url
	 *            完成地址
	 * @param xml
	 *            xml格式的字符串
	 * @param tag
	 *            本次请求的标志
	 * @param callback
	 *            请求结果的回调
	 * 
	 * @throws IOException
	 */
	public void getFromNetworkByXml(String url, String xml, String tag, Callback callback) throws IOException {

		final MediaType xmlType = MediaType.parse("application/xml; charset=utf-8");

		RequestBody body = RequestBody.create(xmlType, xml);
		/**
		 * 将请求体加入请求中
		 */
		Request request = new Request.Builder().url(url).post(body).tag(tag).build();
		/**
		 * 执行请求
		 */
		okClient.newCall(request).enqueue(callback);
	}

	/**
	 * post以流的方式发送json格式的字符串
	 * 
	 * @param url
	 *            完整地址
	 * @param json
	 *            json格式字符串
	 * @param tag
	 *            本次请求的标志
	 * @param callback
	 *            请求结果的回调
	 * 
	 * @throws IOException
	 */
	public void getFromNetworkByJson(String url, String json, String tag, Callback callback) throws IOException {

		final MediaType xmlType = MediaType.parse("application/json; charset=utf-8");

		RequestBody body = RequestBody.create(xmlType, json);
		/**
		 * 将请求体加入请求中
		 */
		Request request = new Request.Builder().url(url).post(body).tag(tag).build();
		/**
		 * 执行请求
		 */
		okClient.newCall(request).enqueue(callback);
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 *            上传地址
	 * @param file
	 *            需要上传的文件
	 * @param tag
	 *            本次请求的标志
	 * 
	 * @param progressListener
	 *            上传进度的监听
	 */
	public void upLoad(String url, File file, String tag, ProgressListener progressListener) {

		// 构造上传请求，类似web表单
		RequestBody requestBody = new MultipartBuilder()
				.type(MultipartBuilder.FORM)
				.addFormDataPart("hello", "android")
				.addFormDataPart("photo", file.getName(), RequestBody.create(null, file))
				.addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""),
						RequestBody.create(MediaType.parse("application/octet-stream"), file)).build();

		// 进行包装，使其支持进度回调
		final Request request = new Request.Builder().url(url)
				.post(ProgressHelper.addProgressRequestListener(requestBody, progressListener)).build();

		okClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {
			}

			@Override
			public void onResponse(Response response) throws IOException {
			}
		});

	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            下载地址
	 * @param tag
	 *            本次请求的标志
	 * @param progressListener
	 *            下载进度的监听
	 */
	public void downLoad(String url, String tag, ProgressListener progressListener) {
		// 构造请求
		final Request request = new Request.Builder().url(url).build();

		// 包装Response使其支持进度回调
		ProgressHelper.addProgressResponseListener(okClient, progressListener).newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {
				Log.e("TAG", "error ", e);
			}

			@Override
			public void onResponse(Response response) throws IOException {
				Log.e("TAG", response.body().string());
			}
		});
	}

	/**
	 * 根据标签取消请求
	 * 
	 * @param tag
	 */
	public void cancelRequest(String tag) {
		okClient.cancel(tag);
	}

}
