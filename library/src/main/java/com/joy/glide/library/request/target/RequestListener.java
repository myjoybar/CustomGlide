package com.joy.glide.library.request.target;

import android.graphics.Bitmap;

/**
 * Created by joybar on 2018/5/24.
 */

public interface RequestListener {
	void onLoadStarted();

	void onProgressUpdate(int value);

	void onResourceReady(Bitmap bitmap);

	void onException(Throwable throwable);

	void onCancelled();


}
