package com.joy.glide.library;

import android.content.Context;
import android.os.Build;

import com.joy.glide.library.cache.MemorySizeCalculator;
import com.joy.glide.library.load.engine.bitmap_recycle.BitmapPool;
import com.joy.glide.library.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.joy.glide.library.load.engine.bitmap_recycle.LruBitmapPool;

/**
 * Created by joybar on 2018/6/8.
 */

public class GlideBuilder {
	private BitmapPool bitmapPool;
	private final Context context;

	public GlideBuilder(Context context) {
		this.context = context;
	}

	Glide createGlide() {

		MemorySizeCalculator calculator = new MemorySizeCalculator(context);
		if (bitmapPool == null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				int size = calculator.getBitmapPoolSize();
				bitmapPool = new LruBitmapPool(size);
			} else {
				bitmapPool = new BitmapPoolAdapter();
			}
		}
		return new Glide(bitmapPool);
	}
}
