package com.joy.glide.library.request.target;

import android.support.annotation.NonNull;

import com.joy.glide.library.request.RequestOrder;

/**
 * Created by joybar on 2018/6/4.
 */

public abstract class BaseTarget <Z> implements Target<Z>  {

	private RequestOrder requestOrder;

	public RequestOrder getRequestOrder() {
		return requestOrder;
	}

	public void setRequestOrder(RequestOrder requestOrder) {
		this.requestOrder = requestOrder;
	}

	@Override
	public void onDataLoaded(Z z) {
		// Do nothing.
	}

	@Override
	public void onDataLoadedError(@NonNull Throwable throwable) {
		// Do nothing.
	}

	@Override
	public void onProgressUpdate(int value) {
		// Do nothing.
	}
}
