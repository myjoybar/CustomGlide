package com.joy.glide.library.ctasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.joy.glide.library.ctasynctask.netexecutor.AbstractExecutor;
import com.joy.glide.library.ctasynctask.netexecutor.IRequestCallback;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joybar on 2018/5/7.
 */

public class HttpUrlConnectionExecutor<TResult> extends AbstractExecutor {

	public HttpUrlConnectionExecutor() {
		super();
	}


	@Override
	public TResult execute(IRequestCallback requestCallback) {

		Bitmap bitmap = null;
		HttpURLConnection con = null;
		TaskOrder taskOrder = getTaskOrder();
		try {
			URL url = new URL(taskOrder.getUrl());
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(3*1000);
			con.setReadTimeout(10 * 1000);
			con.setDoInput(true);
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if(responseCode ==  HttpURLConnection.HTTP_OK){
				bitmap = BitmapFactory.decodeStream(con.getInputStream());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		Result result = (Result) bitmap;
		return result;
	}
}
