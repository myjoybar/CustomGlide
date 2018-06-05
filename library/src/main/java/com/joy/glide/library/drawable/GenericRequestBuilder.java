package com.joy.glide.library.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.key.EmptySignature;
import com.joy.glide.library.data.DataRepository;
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.data.source.local.LocalDataSource;
import com.joy.glide.library.data.source.local.LocalDataSourceInstance;
import com.joy.glide.library.data.source.remote.RemoteDataSource;
import com.joy.glide.library.load.resource.bitmap.ImageHeaderParser;
import com.joy.glide.library.request.GenericRequest;
import com.joy.glide.library.request.RequestOrder;
import com.joy.glide.library.request.target.DrawableImageViewTarget;
import com.joy.glide.library.request.target.PreloadViewTarget;
import com.joy.glide.library.request.target.ViewTarget;
import com.joy.glide.library.resource.DrawableTransformer;
import com.joy.glide.library.resource.ResourceTransformer;
import com.joy.glide.library.utils.CheckUtils;
import com.joy.glide.library.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by joybar on 2018/5/24.
 */

public class GenericRequestBuilder<ModelType> {
	private RequestOrder<ModelType> requestTaskOrder;
	private DataSource.LoadDataListener loadDataListener;
	private int placeholderId;
	private int errorId;
	private ModelType model;
	protected final Class<ModelType> modelClass;
	private LocalDataSource.MemoryCacheStrategy memoryCacheStrategy;
	private LocalDataSource.DiskCacheStrategy diskCacheStrategy;
	private Context context;
	private ViewTarget viewTarget;
	private ResourceTransformer resourceTransformer;

	private DataRepository dataRepository;


	public GenericRequestBuilder(Context context, Class<ModelType> modelClass, ModelType url) {
		this.context = context;
		this.modelClass = modelClass;
		buildRequestTaskOrder(url);

	}

	private void buildRequestTaskOrder(ModelType url) {
		if (RequestOrder.class.isAssignableFrom(url.getClass())) {
			requestTaskOrder = new RequestOrder(((RequestOrder) url).getUrl());
		} else {
			requestTaskOrder = new RequestOrder(url);
		}
	}

	public GenericRequestBuilder<ModelType> load(ModelType model) {
		this.model = model;
		return this;
	}

	public GenericRequestBuilder<ModelType> diskCacheStrategy(LocalDataSource.DiskCacheStrategy strategy) {
		this.diskCacheStrategy = strategy;
		return this;
	}

	public GenericRequestBuilder<ModelType> memoryCacheStrategy(LocalDataSource.MemoryCacheStrategy strategy) {
		this.memoryCacheStrategy = strategy;
		return this;
	}


	public GenericRequestBuilder<ModelType> asDrawable() {
		this.resourceTransformer = new DrawableTransformer();
		return this;
	}

	public void preload() {
		this.into(new PreloadViewTarget(new View(context)));
	}

	public <Y extends ViewTarget> void into(Y target) {
		CheckUtils.checkNotNull(target, "target must not be null!");
		Util.assertMainThread();
		this.viewTarget = target;
		final DrawableKey drawableKey = new DrawableKey(requestTaskOrder.getUrl().toString(), target.getWidth(), target.getHeight(), EmptySignature
				.obtain());
		dataRepository = createDataRepository();
		dataRepository.getDataAsync(drawableKey);

	}

	public void into(final ImageView imageView) {
		//this.into(new BitmapImageViewTarget(imageView));
		this.into(new DrawableImageViewTarget(imageView));
	}

	private DataRepository createDataRepository() {

		LocalDataSource localDataSource = LocalDataSourceInstance.getInstance(context).getLocalDataSource();
		localDataSource.setDiskCacheStrategy(diskCacheStrategy);
		localDataSource.setMemoryCacheStrategy(memoryCacheStrategy);
		DataSource remoteDataSource = new RemoteDataSource(requestTaskOrder);
		GenericRequest<ModelType> genericRequest = GenericRequest.obtain(requestTaskOrder, loadDataListener, placeholderId, errorId, model,
				modelClass, memoryCacheStrategy, diskCacheStrategy, context, viewTarget, resourceTransformer);
		return new DataRepository<Bitmap>(localDataSource, remoteDataSource, genericRequest);

	}

	public GenericRequestBuilder listener(DataSource.LoadDataListener loadDataListener) {
		this.loadDataListener = loadDataListener;
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

	public void cancelRequest() {
		if (null != dataRepository) {
			dataRepository.cancel();
		}
	}

	public DataSource.LoadDataListener getLoadDataListener() {
		return loadDataListener;
	}
}
