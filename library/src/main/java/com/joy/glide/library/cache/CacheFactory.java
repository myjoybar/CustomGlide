package com.joy.glide.library.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.joy.glide.library.cache.disk.LocalDiskCache;
import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.memory.MemoryLruCache;
import com.joy.glide.library.utils.GLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 27/05/2018.
 */

public class CacheFactory {

	private LocalDiskCache diskCache;
	private MemoryLruCache memoryLruCache;
	private CacheStrategy.CacheStrategySwitcher cacheStrategySwitcher;


	public CacheFactory(Context context) {
		memoryLruCache = DefaultConfigurationFactory.createMemoryLruCache();
		diskCache = DefaultConfigurationFactory.createDiskCache(context.getApplicationContext());
	}

	public void setCacheStrategy(CacheStrategy.CacheStrategySwitcher cacheStrategySwitcher) {
		this.cacheStrategySwitcher = cacheStrategySwitcher;
	}

	public Bitmap get(DrawableKey key) throws FileNotFoundException {

		if (null == cacheStrategySwitcher) {
			return null;
		}
		Bitmap bitmap = null;
		if (cacheStrategySwitcher.isMemoryCacheEnable) {
			bitmap = memoryLruCache.get(key);
			if (null != bitmap) {
				return bitmap;
			}
		}
		if (cacheStrategySwitcher.isDiskCacheEnable) {
			File imageFile = diskCache.get(key);
			if (imageFile != null && imageFile.exists() && imageFile.length() > 0) {
				// to to opt
				bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
				if (null != bitmap) {
					return bitmap;
				}
			}

		}
		return bitmap;

	}

	public void getAsync(final DrawableKey key, final CacheLoader cacheLoader) {
		if (null == cacheStrategySwitcher) {
			cacheLoader.onFailure();
			return;
		}
		AsynchronousTask<Void, Bitmap> asynchronousTask = new AsynchronousTask<Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground() {
				if (null == cacheStrategySwitcher) {
					return null;
				}
				Bitmap bitmap = null;
				if (cacheStrategySwitcher.isMemoryCacheEnable) {
					bitmap = memoryLruCache.get(key);
					if (null != bitmap) {
						GLog.printInfo("load from memoryLruCache onSuccess");
						return bitmap;
					}
				}
				if (cacheStrategySwitcher.isDiskCacheEnable) {
					File imageFile = diskCache.get(key);
					if (imageFile != null && imageFile.exists() && imageFile.length() > 0) {
						// to to opt
						try {
							bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						if (null != bitmap) {
							GLog.printInfo("load from diskCache onSuccess");
							return bitmap;
						}
					}

				}
				return bitmap;

			}

			@Override
			protected void onPostExecute(Bitmap bitmap) {
				super.onPostExecute(bitmap);
				if (null != bitmap) {
					cacheLoader.onSuccess(bitmap);
				} else {
					cacheLoader.onFailure();
				}
			}
		};

		asynchronousTask.execute();
	}


	public void save(final DrawableKey key, final Bitmap bitmap) {
		AsynchronousTask<Void, Void> asyncTask = new AsynchronousTask<Void, Void>() {

			@Override
			protected Void doInBackground() {
				memoryLruCache.put(key, bitmap);
				try {
					diskCache.save(key, bitmap);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		asyncTask.execute();


	}

	public interface CacheLoader {
		void onSuccess(Bitmap bitmap);

		void onFailure();
	}

}
