package com.joy.glide.library.request;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.joy.glide.library.helper.asynfactory.AsyncFactoryHelper;
import com.joy.glide.library.helper.asynfactory.Callback;
import com.joy.glide.library.utils.GLog;
import com.joy.smoothhttp.call.ICall;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

/**
 * Created by joybar on 2018/5/9.
 */

public class RequestHolder {
	private RequestOrder requestTaskOrder;
	private ICall mICall;

	public RequestHolder(String url) {
		requestTaskOrder = new RequestOrder(url);
	}

	public void cancelRequest() {
		if (null != mICall) {
			mICall.cancel();
		}
	}


	public void into(final ImageView imageView) {
		AsyncFactoryHelper.getInstance().produce(requestTaskOrder, new Callback<Bitmap>() {

			@Override
			public void onPreExecute(ICall iCall) {
				mICall = iCall;
			}

			@Override
			public void onFailure(RequestOrder requestOrder, Throwable throwable) {
				GLog.print(throwable.getMessage());
			}

			@Override
			public void onResponse(RequestOrder requestOrder, Bitmap bitmap) {
				GLog.print("onResponse=" + requestOrder.getUrl());
				imageView.setImageBitmap(bitmap);
			}


		});

	}
}
