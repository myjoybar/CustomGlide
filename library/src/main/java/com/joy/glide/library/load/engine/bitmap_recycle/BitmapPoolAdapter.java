package com.joy.glide.library.load.engine.bitmap_recycle;

import android.graphics.Bitmap;

/**
 * Created by joybar on 2018/6/8.
 */

public class BitmapPoolAdapter implements BitmapPool {
	@Override
	public int getMaxSize() {
		return 0;
	}

	@Override
	public void setSizeMultiplier(float sizeMultiplier) {
		// Do nothing.
	}

	@Override
	public boolean put(Bitmap bitmap) {
		return false;
	}

	@Override
	public Bitmap get(int width, int height, Bitmap.Config config) {
		return null;
	}

	@Override
	public Bitmap getDirty(int width, int height, Bitmap.Config config) {
		return null;
	}

	@Override
	public void clearMemory() {
		// Do nothing.
	}

	@Override
	public void trimMemory(int level) {
		// Do nothing.
	}
}
