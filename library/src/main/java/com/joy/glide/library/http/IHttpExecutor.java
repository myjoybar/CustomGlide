package com.joy.glide.library.http;

/**
 * Created by joybar on 2018/5/11.
 */

public interface IHttpExecutor<TResult> {
	TResult execute();

	TResult execute(IRequestCallback requestCallback);
}
