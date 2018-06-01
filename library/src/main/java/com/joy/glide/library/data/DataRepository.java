package com.joy.glide.library.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.utils.GLog;

/**
 * Created by joybar on 2018/5/30.
 */

public class DataRepository implements DataSource {

	private static DataRepository INSTANCE = null;

	DataSource localDataSource;
	DataSource remoteDataSource;

	public DataRepository(DataSource localDataSource, DataSource remoteDataSource) {
		this.localDataSource = localDataSource;
		this.remoteDataSource = remoteDataSource;
	}

	public DataSource getLocalDataSource() {
		return localDataSource;
	}

	public static DataRepository getInstance(DataSource localDataSource, DataSource remoteDataSource) {
		if (INSTANCE == null) {
			INSTANCE = new DataRepository(localDataSource, remoteDataSource);
		}
		return INSTANCE;
	}

	@Override
	public void saveData(@NonNull DrawableKey key, Bitmap bitmap) {
		localDataSource.saveData(key, bitmap);
	}

	@Override
	public Bitmap getData(@NonNull DrawableKey key) {
		return null;
	}

	@Override
	public void getDataAsync(@NonNull final DrawableKey key, final LoadDataCallback loadDataCallback) {
		localDataSource.getDataAsync(key, new LoadDataCallback() {
			@Override
			public void onDataLoaded(Bitmap bitmap) {
				loadDataCallback.onDataLoaded(bitmap);
			}

			@Override
			public void onDataLoadedError(@NonNull Throwable throwable) {
				GLog.printWarning("load form local data,onDataLoadedError");
				remoteDataSource.getDataAsync(key, new LoadDataCallback() {
					@Override
					public void onDataLoaded(Bitmap bitmap) {
						loadDataCallback.onDataLoaded(bitmap);
					}

					@Override
					public void onDataLoadedError(@NonNull Throwable throwable) {
						GLog.printWarning("load form remote data,onDataLoadedError");
						loadDataCallback.onDataLoadedError(throwable);
					}

					@Override
					public void onProgressUpdate(int value) {
						loadDataCallback.onProgressUpdate(value);
					}
				});
			}

			@Override
			public void onProgressUpdate(int value) {
				//nothing to do
			}
		});
	}

	@Override
	public void deleteAll() {
		localDataSource.deleteAll();
		remoteDataSource.deleteAll();
	}

	@Override
	public void delete(@NonNull DrawableKey key) {
		localDataSource.delete(key);
		remoteDataSource.delete(key);
	}

	@Override
	public void cancel() {
		remoteDataSource.cancel();
	}
}
