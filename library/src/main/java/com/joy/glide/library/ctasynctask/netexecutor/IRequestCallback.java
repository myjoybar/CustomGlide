package com.joy.glide.library.ctasynctask.netexecutor;

/**
 * Created by joybar on 2018/5/7.
 */

public interface IRequestCallback<T> {

	void onSuccess(T response);

	void onFailure(Throwable throwable);
}
