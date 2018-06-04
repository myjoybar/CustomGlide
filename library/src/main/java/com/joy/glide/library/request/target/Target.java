package com.joy.glide.library.request.target;

import android.support.annotation.NonNull;

/**
 * Created by joybar on 2018/6/4.
 */

/**
 * @param <R> The type of resource the target can display.
 */
public interface Target<R> {
	int SIZE_ORIGINAL = Integer.MIN_VALUE;

	void onDataLoaded(R r);

	void onDataLoadedError(@NonNull Throwable throwable);

	void onProgressUpdate(int value);

}
