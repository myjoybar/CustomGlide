package com.joy.glide.libasync.factory;

/**
 * Created by joybar on 2018/5/9.
 */

public interface Callback<TResult> {
	void onFailure();
	void onResponse(TResult result);
}
