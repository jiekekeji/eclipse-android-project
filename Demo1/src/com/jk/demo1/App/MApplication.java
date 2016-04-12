package com.jk.demo1.App;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import android.app.Application;
import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.finalteam.okhttpfinal.Part;

public class MApplication extends Application {

	private static int REQ_TIMEOUT=3500;
	@Override
	public void onCreate() {
		super.onCreate();
		initOkHttpFinal();
	}

	private void initOkHttpFinal() {
		List<Part> commomParams = new ArrayList<Part>();
		Headers commonHeaders = new Headers.Builder().build();

		List<Interceptor> interceptorList = new ArrayList<Interceptor>();
		OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder()
		        .setCommenParams(commomParams)
				.setCommenHeaders(commonHeaders)
				.setTimeout(REQ_TIMEOUT).setInterceptors(interceptorList)
				.setDebug(true);
		OkHttpFinal.getInstance().init(builder.build());
	}
}
