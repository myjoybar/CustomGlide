package com.joy.glide.library.load.engine.bitmap_recycle;

import android.graphics.Bitmap;

/**
 * Created by joybar on 2018/6/8.
 */
interface LruPoolStrategy {
	void put(Bitmap bitmap);
	Bitmap get(int width, int height, Bitmap.Config config);
	Bitmap removeLast();
	String logBitmap(Bitmap bitmap);
	String logBitmap(int width, int height, Bitmap.Config config);
	int getSize(Bitmap bitmap);
}
