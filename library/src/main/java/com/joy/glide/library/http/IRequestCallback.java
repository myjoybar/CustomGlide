package com.joy.glide.library.http;

/**
 * Created by joybar on 2018/5/7.
 */

public interface IRequestCallback<TResult> {

	void onSuccess(TResult response);

	void onFailure(Throwable throwable);
}
