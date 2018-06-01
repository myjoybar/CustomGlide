package com.joy.glide.library.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.joy.glide.library.cache.key.DrawableKey;

import java.io.FileNotFoundException;

/**
 * Created by joybar on 2018/5/29.
 */

public class CacheStrategy {

	public CacheFactory cacheFactory;


	public static CacheStrategy getInstance() {
		return CacheStrategyHolder.INSTANCE;
	}

	public static class CacheStrategyHolder {
		private static CacheStrategy INSTANCE = new CacheStrategy();
	}

	public void initCacheFactory(Context context, CacheStrategy.CacheStrategySwitcher cacheStrategySwitcher) {
		if (null == cacheFactory) {
			cacheFactory = new CacheFactory(context);
		}
		cacheFactory.setCacheStrategy(cacheStrategySwitcher);
	}

	public Bitmap get(DrawableKey key) throws FileNotFoundException {
		if (null != cacheFactory) {
			return cacheFactory.get(key);
		}
		return null;
	}

	public void getAsync(final DrawableKey key, final CacheFactory.CacheLoader cacheLoader) {
		if (null != cacheFactory) {
			cacheFactory.getAsync(key, cacheLoader);
		}else{
			cacheLoader.onFailure();
		}
	}

	public void save(final DrawableKey key, final Bitmap bitmap) {
		if (null != cacheFactory) {
			cacheFactory.save(key, bitmap);
		}
	}

	public static class CacheStrategySwitcher {
		boolean isMemoryCacheEnable;
		boolean isDiskCacheEnable;

		public CacheStrategySwitcher(boolean isMemoryCacheEnable, boolean isDiskCacheEnable) {
			this.isMemoryCacheEnable = isMemoryCacheEnable;
			this.isDiskCacheEnable = isDiskCacheEnable;
		}

	}

}
