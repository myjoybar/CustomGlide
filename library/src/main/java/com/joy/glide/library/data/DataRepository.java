package com.joy.glide.library.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.load.resource.bitmap.ImageHeaderParser;
import com.joy.glide.library.request.GenericRequest;
import com.joy.glide.library.request.target.ViewTarget;
import com.joy.glide.library.utils.GLog;
import com.joy.smoothhttp.response.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import pl.droidsonroids.gif.GifDrawable;

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
				Bitmap bitmap = (Bitmap) resource;
				Drawable drawable = new BitmapDrawable(genericRequest.getContext().getResources(), bitmap);
				attachDataToTarget(key, drawable, bitmap, loadDataListener, viewTarget, false);
			}

			@Override
			public void onDataLoadedError(@NonNull Throwable throwable) {
				GLog.printWarning("load form local data,onDataLoadedError");
				remoteDataSource.getDataAsync(key, new LoadDataListenerAdapter<Response>() {
					@Override
					public void onDataLoaded(Response response) {
						try {
							Drawable drawable = null;
							Bitmap bitmap = BitmapFactory.decodeByteArray(response.getResponseBody().getBytes(), 0, response.getResponseBody()
									.getBytes().length);
							if (isGif(response.getResponseBody().getBytes())) {
								GLog.printInfo("this is gif ");
								byte[] rawGifBytes = response.getResponseBody().getBytes();
								drawable = new GifDrawable(rawGifBytes);
							} else {
								GLog.printInfo("this is not gif ");
								drawable = new BitmapDrawable(genericRequest.getContext().getResources(), bitmap);
							}
							attachDataToTarget(key, drawable, bitmap, loadDataListener, viewTarget, true);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onDataLoadedError(@NonNull Throwable throwable) {
						GLog.printWarning("load form remote data,onDataLoadedError");
						if (null != viewTarget) {
							viewTarget.setErrorHolder(genericRequest.getErrorId());
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

	private boolean isGif(byte[] bytes) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(bytes);
		ImageHeaderParser.ImageType type = new ImageHeaderParser(inputStream).getType();
		return type == ImageHeaderParser.ImageType.GIF;
	}

	private byte[] bitmapToByteArray(Bitmap bitmap) {
		int bytes = bitmap.getByteCount();

		ByteBuffer buf = ByteBuffer.allocate(bytes);
		bitmap.copyPixelsToBuffer(buf);
		byte[] byteArray = buf.array();
		return byteArray;
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

	private void attachDataToTarget(DrawableKey key, Drawable drawable, Bitmap bitmap, LoadDataListener loadDataListener, ViewTarget viewTarget,
									boolean isSavedData) {
		if (null != loadDataListener) {
			loadDataListener.onDataLoaded(drawable);
		}
		viewTarget.onDataLoaded(drawable);
		if (isSavedData) {
			saveData(key, (R) bitmap);
		}
	}

}
