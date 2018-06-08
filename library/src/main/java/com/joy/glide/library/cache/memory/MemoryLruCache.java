package com.joy.glide.library.cache.memory;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.LruCache;

import com.joy.glide.library.cache.key.Key;
import com.joy.glide.library.utils.GLog;

import java.util.Collection;

/**
 * Created by joybar on 27/05/2018.
 */

public class MemoryLruCache implements MemoryCache {

	private static LruCache<Key, Bitmap> mMemoryCache;

	private int availableMemoryPercent;
	public MemoryLruCache() {
		this(50);
	}

	public MemoryLruCache(int availableMemoryPercent) {
		if (availableMemoryPercent <= 0 || availableMemoryPercent >= 100) {
			throw new IllegalArgumentException("availableMemoryPercent must be in range (0 < % < 100)");
		}
		GLog.printInfo("availableMemoryPercent= "+availableMemoryPercent);
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory *availableMemoryPercent/ 100;
		mMemoryCache = new LruCache<Key, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(Key key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	@Override
	public boolean put(Key key, Bitmap value) {
		if (get(key) == null) {
			mMemoryCache.put(key, value);
			return true;
		}
		return false;

	}

	@Override
	public Bitmap get(Key key) {
		return mMemoryCache.get(key);
	}

	@Override
	public Bitmap remove(Key key) {
		return mMemoryCache.remove(key);
	}

	@Override
	public Collection<Key> keys() {
		return null;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public void clear() {
		mMemoryCache.trimToSize(-1);
	}


}
