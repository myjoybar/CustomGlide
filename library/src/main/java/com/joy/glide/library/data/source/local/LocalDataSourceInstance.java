package com.joy.glide.library.data.source.local;

import android.content.Context;

/**
 * Created by joybar on 2018/6/1.
 */

public class LocalDataSourceInstance {

	private static LocalDataSourceInstance INSTANCE = null;
	LocalDataSource localDataSource;

	public static LocalDataSourceInstance getInstance(Context context) {
		if (null == INSTANCE) {
			INSTANCE = new LocalDataSourceInstance(context);
		}
		return INSTANCE;
	}

	public LocalDataSourceInstance(Context context) {
		localDataSource = new LocalDataSource(context);
	}

	public void setCacheStrategy(LocalDataSource.CacheStrategySwitcher cacheStrategy) {
		localDataSource.setCacheStrategy(cacheStrategy);
	}

	public LocalDataSource getLocalDataSource() {
		return localDataSource;
	}
}
