package com.joy.glide.library.data;

import android.support.annotation.NonNull;

import com.joy.glide.library.cache.key.DrawableKey;

/**
 * Created by joybar on 2018/5/30.
 */

public interface DataSource<R> {

	interface LoadDataListener<R> {

		void onLoadStarted();

		void onDataLoaded(R resource);

		void onDataLoadedError(@NonNull Throwable throwable);

		void onProgressUpdate(int value);

		void onCancelled();

	}

	abstract class LoadDataListenerAdapter<R> implements LoadDataListener<R> {
		@Override
		public void onLoadStarted() {

		}

		@Override
		public void onDataLoaded(R resource) {

		}

		@Override
		public void onDataLoadedError(@NonNull Throwable throwable) {

		}

		@Override
		public void onProgressUpdate(int value) {

		}

		@Override
		public void onCancelled() {

		}
	}


	void saveData(@NonNull DrawableKey key, R resource);

	R getData(@NonNull DrawableKey key);

	void getDataAsync(@NonNull DrawableKey key);

	void getDataAsync(@NonNull DrawableKey key, LoadDataListener loadDataCallback);

	void deleteAll();

	void delete(@NonNull DrawableKey key);

	void cancel();

}
