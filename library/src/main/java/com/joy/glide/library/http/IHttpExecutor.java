package com.joy.glide.library.http;

import com.joy.glide.library.ctasynctask.netexecutor.IRequestCallback;

/**
 * Created by joybar on 2018/5/11.
 */

public interface IHttpExecutor<TResult> {
	
	void execute(IRequestCallback requestCallback);
}
