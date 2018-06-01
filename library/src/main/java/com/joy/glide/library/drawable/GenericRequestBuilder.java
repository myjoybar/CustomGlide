package com.joy.glide.library.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.key.EmptySignature;
import com.joy.glide.library.data.DataRepository;
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.data.source.local.LocalDataSource;
import com.joy.glide.library.data.source.local.LocalDataSourceInstance;
import com.joy.glide.library.data.source.remote.RemoteDataSource;
import com.joy.glide.library.load.engine.DiskCacheStrategy;
import com.joy.glide.library.load.resource.bitmap.ImageHeaderParser;
import com.joy.glide.library.request.RequestOrder;
import com.joy.glide.library.request.target.RequestListener;
import com.joy.glide.library.utils.GLog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by joybar on 2018/5/24.
 */

public class GenericRequestBuilder<ModelType> {
	private RequestOrder<ModelType> requestTaskOrder;
	private RequestListener requestListener;
	private int placeholderId;
	private int errorId;
	private ModelType model;
	protected final Class<ModelType> modelClass;
	private DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.RESULT;
	private LocalDataSource.CacheStrategySwitcher cacheStrategySwitcher;
	private Context context;
	DataRepository dataRepository;

	public GenericRequestBuilder(Context context, Class<ModelType> modelClass, ModelType url) {
		this.context = context;
		this.modelClass = modelClass;
		requestTaskOrder = new RequestOrder(url);
	}

	public GenericRequestBuilder<ModelType> load(ModelType model) {
		this.model = model;
		return this;
	}

	public GenericRequestBuilder<ModelType> diskCacheStrategy(DiskCacheStrategy strategy) {
		this.diskCacheStrategy = strategy;
		return this;
	}

	public GenericRequestBuilder<ModelType> cacheStrategySwitcher(LocalDataSource.CacheStrategySwitcher cacheStrategySwitcher) {
		this.cacheStrategySwitcher = cacheStrategySwitcher;
		return this;
	}

	private void prepareDataRepository() {

		LocalDataSource localDataSource = LocalDataSourceInstance.getInstance(context).getLocalDataSource();
		localDataSource.setCacheStrategy(cacheStrategySwitcher);
		DataSource remoteDataSource = new RemoteDataSource(requestTaskOrder);
		dataRepository = new DataRepository(localDataSource, remoteDataSource);

	}


	public void into(final ImageView imageView) {

		GLog.printInfo("getWidth=" + imageView.getWidth());
		GLog.printInfo("getHeight=" + imageView.getHeight());

		handleLoadStart(imageView);
		prepareDataRepository();

		//load from cache
		final DrawableKey drawableKey = new DrawableKey(requestTaskOrder.getUrl().toString(), imageView.getWidth(), imageView.getHeight(),
				EmptySignature.obtain());

		dataRepository.getDataAsync(drawableKey, new DataSource.LoadDataCallback() {
			@Override
			public void onDataLoaded(Bitmap bitmap) {
				handleLoadReady(bitmap);
				handleSetImageResource(bitmap, null, imageView);
				dataRepository.saveData(drawableKey, bitmap);
			}

			@Override
			public void onDataLoadedError(@NonNull Throwable throwable) {
				GLog.printInfo(throwable.getMessage());
				handleLoadError(imageView, throwable);
			}

			@Override
			public void onProgressUpdate(int value) {
				handleLoadProgressUpdate(value);
			}
		});

	}

	public GenericRequestBuilder listener(RequestListener requestListener) {
		this.requestListener = requestListener;
		return this;
	}

	public GenericRequestBuilder placeholder(int resourceId) {
		this.placeholderId = resourceId;
		return this;
	}

	public GenericRequestBuilder error(int resourceId) {
		this.errorId = resourceId;
		return this;
	}

	public void handleLoadStart(ImageView imageView) {
		if (null != requestListener) {
			requestListener.onLoadStarted();
		}
		if (0 != placeholderId) {
			imageView.setImageResource(placeholderId);
		}
	}

	private void handleLoadProgressUpdate(int values) {
		if (null != requestListener) {
			requestListener.onProgressUpdate(values);
		}
	}

	private void handleLoadReady(Bitmap bitmap) {
		if (null != requestListener) {
			requestListener.onResourceReady(bitmap);
		}
	}

	public byte[] getBytesByBitmap(Bitmap bitmap) {
		ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
		return buffer.array();
	}

	private boolean isGif(byte[] bytes, Bitmap bitmap) throws IOException {
		if (null == bytes) {
			bytes = getBytesByBitmap(bitmap);
		}
		InputStream inputStream = new ByteArrayInputStream(bytes);
		ImageHeaderParser.ImageType type = new ImageHeaderParser(inputStream).getType();
		return type == ImageHeaderParser.ImageType.GIF;
	}

	private void handleSetImageResource(Bitmap bitmap, GifDrawable gifDrawable, ImageView imageView) {

		if (null != gifDrawable) {
			GLog.printInfo("this is  gif");
			imageView.setImageDrawable(gifDrawable);
		} else {
			GLog.printInfo("this is not gif");
			imageView.setImageBitmap(bitmap);
		}

	}

	private void handleLoadError(ImageView imageView, Throwable throwable) {
		if (0 != errorId) {
			imageView.setImageResource(errorId);
		}
		if (null != requestListener) {
			requestListener.onException(throwable);
		}
	}


	public void cancelRequest() {
		if (null != dataRepository) {
			dataRepository.cancel();
		}
	}

	public RequestListener getRequestListener() {
		return requestListener;
	}

}
