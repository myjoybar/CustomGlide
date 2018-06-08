package com.joy.glide.library.load.resource.bitmap;

import android.graphics.Bitmap;

import com.joy.glide.library.load.engine.Resource;
import com.joy.glide.library.load.engine.bitmap_recycle.BitmapPool;
import com.joy.glide.library.utils.Util;

/**
 * A resource wrapping a {@link android.graphics.Bitmap} object.
 */
public class BitmapResource implements Resource<Bitmap> {
	private final Bitmap bitmap;
	private final BitmapPool bitmapPool;


	public static BitmapResource obtain(Bitmap bitmap, BitmapPool bitmapPool) {
		if (bitmap == null) {
			return null;
		} else {
			return new BitmapResource(bitmap, bitmapPool);
		}
	}

	public BitmapResource(Bitmap bitmap, BitmapPool bitmapPool) {
		if (bitmap == null) {
			throw new NullPointerException("Bitmap must not be null");
		}
		if (bitmapPool == null) {
			throw new NullPointerException("BitmapPool must not be null");
		}
		this.bitmap = bitmap;
		this.bitmapPool = bitmapPool;
	}

	@Override
	public Bitmap get() {
		return bitmap;
	}

	@Override
	public int getSize() {
		return Util.getBitmapByteSize(bitmap);
	}

	@Override
	public void recycle() {
		if (!bitmapPool.put(bitmap)) {
			bitmap.recycle();
		}
	}
}
