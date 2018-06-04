package com.joy.glide.library.data;

import android.support.annotation.NonNull;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.request.GenericRequest;
import com.joy.glide.library.request.target.ViewTarget;
import com.joy.glide.library.resource.ResourceTransformer;
import com.joy.glide.library.utils.GLog;

/**
 * Created by joybar on 2018/5/30.
 */

public class DataRepository<R> implements DataSource<R> {

	private static DataRepository INSTANCE = null;

	private DataSource localDataSource;
	private DataSource remoteDataSource;
	private GenericRequest genericRequest;

	public DataRepository(DataSource localDataSource, DataSource remoteDataSource, GenericRequest genericRequest) {
		this.localDataSource = localDataSource;
		this.remoteDataSource = remoteDataSource;
		this.genericRequest = genericRequest;
	}

	public DataSource getLocalDataSource() {
		return localDataSource;
	}

	public static DataRepository getInstance(DataSource localDataSource, DataSource remoteDataSource, GenericRequest genericRequest) {
		if (INSTANCE == null) {
			INSTANCE = new DataRepository(localDataSource, remoteDataSource, genericRequest);
		}
		return INSTANCE;
	}

	@Override
	public void saveData(@NonNull DrawableKey key, R resource) {
		localDataSource.saveData(key, resource);
	}

	@Override
	public R getData(@NonNull DrawableKey key) {
		return null;
	}

	@Override
	public void getDataAsync(@NonNull final DrawableKey key) {
		final LoadDataListener loadDataListener = genericRequest.getRequestListener();
		final ViewTarget viewTarget = genericRequest.getViewTarget();
		loadDataListener.onLoadStarted();
		viewTarget.setPlaceHolder(genericRequest.getPlaceholderId());
		localDataSource.getDataAsync(key, new LoadDataListenerAdapter<R>() {
			@Override
			public void onDataLoaded(R resource) {
				dataLoaded(key, resource, loadDataListener, viewTarget, true);
			}

			@Override
			public void onDataLoadedError(@NonNull Throwable throwable) {
				GLog.printWarning("load form local data,onDataLoadedError");
				remoteDataSource.getDataAsync(key, new LoadDataListenerAdapter<R>() {
					@Override
					public void onDataLoaded(R resource) {
						dataLoaded(key, resource, loadDataListener, viewTarget, true);
					}

					@Override
					public void onDataLoadedError(@NonNull Throwable throwable) {
						GLog.printWarning("load form remote data,onDataLoadedError");
						if (null != viewTarget) {
							viewTarget.onDataLoaded(genericRequest.getErrorId());
						}
						if (null != loadDataListener) {
							loadDataListener.onDataLoadedError(throwable);
						}
					}

					@Override
					public void onProgressUpdate(int value) {
						if (null != loadDataListener) {
							loadDataListener.onProgressUpdate(value);
						}
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
	public void getDataAsync(@NonNull final DrawableKey key, final LoadDataListener loadDataCallback) {
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

	private void dataLoaded(DrawableKey key, R resource, LoadDataListener loadDataListener, ViewTarget viewTarget, boolean isSavedData) {
		if (null != loadDataListener) {
			loadDataListener.onDataLoaded(resource);
		}
		ResourceTransformer resourceTransformer = genericRequest.getResourceTransformer();
		if (null != resourceTransformer && null != viewTarget) {
			viewTarget.onDataLoaded(resourceTransformer.transform(resource));
		} else {
			viewTarget.onDataLoaded(resource);
		}
		if (isSavedData) {
			saveData(key, resource);
		}
	}

}
