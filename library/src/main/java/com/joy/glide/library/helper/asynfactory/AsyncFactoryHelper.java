package com.joy.glide.library.helper.asynfactory;


import android.graphics.Bitmap;

import com.joy.glide.library.request.RequestOrder;
import com.joy.smoothhttp.SmoothHttpClient;
import com.joy.smoothhttp.call.ICall;
import com.joy.smoothhttp.convert.BitmapConverter;
import com.joy.smoothhttp.request.Request;

/**
 * Created by joybar on 2018/5/11.
 */

public class AsyncFactoryHelper<TResult> {

	public AsyncFactoryHelper() {

	}
	public static AsyncFactoryHelper getInstance() {
		return FactoryManagerHolder.INSTANCE;
	}

	private static class FactoryManagerHolder {
		private static AsyncFactoryHelper INSTANCE = new AsyncFactoryHelper();
	}


	public void produce(final RequestOrder taskOrder, final Callback callback) {

		SmoothHttpClient smoothHttpClient = new SmoothHttpClient();
		final Request request = new Request.Builder().setHttpUrl(taskOrder.getUrl()).build();
		ICall<Bitmap> call = smoothHttpClient.newCall(request, new BitmapConverter());
		callback.onPreExecute(call);
		call.submit(new com.joy.smoothhttp.call.AbCallback<Bitmap>() {
			@Override
			public void onFailure(ICall call, Throwable throwable) {
				callback.onFailure(taskOrder, throwable);
			}

			@Override
			public void onResponse(ICall call, Bitmap bitmap) {
				callback.onResponse(taskOrder, bitmap);

			}

			@Override
			public void onProgressUpdate(ICall call, int values) {
				super.onProgressUpdate(call, values);
				callback.onProgressUpdate(taskOrder,values);
			}
		});
	}


}
