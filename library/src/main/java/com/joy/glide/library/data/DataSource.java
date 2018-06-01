package com.joy.glide.library.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.joy.glide.library.cache.key.DrawableKey;

/**
 * Created by joybar on 2018/5/30.
 */

public interface DataSource {

	interface LoadDataCallback {

		void onDataLoaded(Bitmap bitmap);

		void onDataLoadedError(@NonNull Throwable throwable);

		void onProgressUpdate(int value);

	}

	void saveData(@NonNull DrawableKey key, Bitmap bitmap);

	Bitmap getData(@NonNull DrawableKey key);

	void getDataAsync(@NonNull DrawableKey key, LoadDataCallback loadDataCallback);

	void deleteAll();

	void delete(@NonNull DrawableKey key);

	void cancel();

}
