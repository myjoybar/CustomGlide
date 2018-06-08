package com.joy.glide.library.helper.asynfactory;


import com.joy.glide.library.request.RequestOrder;
import com.joy.smoothhttp.SmoothHttpClient;
import com.joy.smoothhttp.call.ICall;
import com.joy.smoothhttp.convert.ResponseConverter;
import com.joy.smoothhttp.request.Request;
import com.joy.smoothhttp.response.Response;

/**
 * Created by joybar on 2018/5/11.
 */

public class AsyncFactoryHelper<TResult> {

	private static final int TIME_OUT = 60*1000;
	private static final int MAX_RUNNING_TASKS = 16;
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
		final Request request = new Request.Builder().setHttpUrl((String) taskOrder.getUrl()).setTimeOut(TIME_OUT).setMaxRunningTaskNumber(MAX_RUNNING_TASKS).build();
		ICall<Response> call = smoothHttpClient.newCall(request, new ResponseConverter());
		callback.onPreExecute(call);
		call.submit(new com.joy.smoothhttp.call.AbCallback<Response>() {
			@Override
			public void onFailure(ICall call, Throwable throwable) {
				callback.onFailure(taskOrder, throwable);
				callback.onFinish(taskOrder);
			}

			@Override
			public void onResponse(ICall call, Response response) {
				callback.onResponse(taskOrder, response);
				callback.onFinish(taskOrder);

			}

			@Override
			public void onProgressUpdate(ICall call, int values) {
				super.onProgressUpdate(call, values);
				callback.onProgressUpdate(taskOrder,values);
			}
		});
	}


}
