package com.joy.glide.library.helper.asynfactory;


import com.joy.glide.library.request.RequestOrder;
import com.joy.smoothhttp.call.ICall;

/**
 * Created by joybar on 2018/5/9.
 */

public interface Callback<TResult> {
	void onPreExecute(ICall iCall);

	void onFailure(RequestOrder requestOrder, Throwable throwable);

	void onResponse(RequestOrder requestOrder, TResult result);
}
