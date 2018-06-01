package com.joy.glide.library.data.source.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.joy.glide.library.cache.DefaultConfigurationFactory;
import com.joy.glide.library.cache.disk.LocalDiskCache;
import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.memory.MemoryLruCache;
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.utils.GLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 2018/5/30.
 */

public class LocalDataSource implements DataSource {

	private LocalDiskCache diskCache;
	private MemoryLruCache memoryLruCache;
	private CacheStrategySwitcher cacheStrategySwitcher;
	private Exception exception;

	public static class CacheStrategySwitcher {
		boolean isMemoryCacheEnable;
		boolean isDiskCacheEnable;

		public CacheStrategySwitcher(boolean isMemoryCacheEnable, boolean isDiskCacheEnable) {
			this.isMemoryCacheEnable = isMemoryCacheEnable;
			this.isDiskCacheEnable = isDiskCacheEnable;
		}
	}

	public Exception getException(String msg) {
		if (null == exception) {
			exception = new Exception(msg);
		} return exception;
	}

	public LocalDataSource(Context context) {
		memoryLruCache = DefaultConfigurationFactory.createMemoryLruCache();
		diskCache = DefaultConfigurationFactory.createDiskCache(context.getApplicationContext());
	}

	public void setCacheStrategy(CacheStrategySwitcher cacheStrategySwitcher) {
		this.cacheStrategySwitcher = cacheStrategySwitcher;
	}

	@Override
	public void saveData(@NonNull final DrawableKey key, final Bitmap bitmap) {
		AsynchronousTask<Void, Void> asyncTask = new AsynchronousTask<Void, Void>() {

			@Override
			protected Void doInBackground() {
				if (cacheStrategySwitcher.isMemoryCacheEnable) {
					GLog.printInfo("save to memory");
					memoryLruCache.put(key, bitmap);

				}
				if (cacheStrategySwitcher.isDiskCacheEnable) {
					try {
						GLog.printInfo("save to disk");
						diskCache.save(key, bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				return null;
			}
		};
		asyncTask.execute();
	}

	@Override
	public Bitmap getData(@NonNull DrawableKey key) {

		if (null == cacheStrategySwitcher) {
			return null;
		}
		Bitmap bitmap = null;
		if (cacheStrategySwitcher.isMemoryCacheEnable) {
			GLog.printInfo("strat load form memory data");
			bitmap = memoryLruCache.get(key);
			if (null != bitmap) {
				return bitmap;
			}
		}
		if (cacheStrategySwitcher.isDiskCacheEnable) {
			GLog.printInfo("strat load form disk data");
			File imageFile = diskCache.get(key);
			if (imageFile != null && imageFile.exists() && imageFile.length() > 0) {
				// to to opt
				try {
					bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
					if (null != bitmap) {
						return bitmap;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}

		}
		return bitmap;
	}

	@Override
	public void getDataAsync(@NonNull final DrawableKey key, final LoadDataCallback loadDataCallback) {
		if (null == cacheStrategySwitcher) {
			loadDataCallback.onDataLoadedError(getException("cacheStrategySwitcher must be init"));
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
					GLog.printInfo("strat load form memory data");
					bitmap = memoryLruCache.get(key);
					if (null != bitmap) {
						GLog.printInfo("load from memoryLruCache onSuccess");
						return bitmap;
					}
				}
				if (cacheStrategySwitcher.isDiskCacheEnable) {
					GLog.printInfo("strat load form disk data");
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
					loadDataCallback.onDataLoaded(bitmap);
				} else {
					loadDataCallback.onDataLoadedError(getException("bitmap is null"));
				}
			}
		};

		asynchronousTask.execute();
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public void deleteAll() {
		memoryLruCache.clear();
	}

	@Override
	public void delete(@NonNull DrawableKey key) {
		diskCache.clear();
		memoryLruCache.remove(key);
	}

	@Override
	public void cancel() {
		//nothing to do
	}


}
