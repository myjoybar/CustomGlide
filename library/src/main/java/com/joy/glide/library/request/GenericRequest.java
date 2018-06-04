package com.joy.glide.library.request;

import android.content.Context;

import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.data.source.local.LocalDataSource;
import com.joy.glide.library.request.target.ViewTarget;
import com.joy.glide.library.resource.ResourceTransformer;

/**
 * Created by joybar on 2018/6/4.
 */

public class GenericRequest<ModelType> {

	private RequestOrder<ModelType> requestTaskOrder;
	DataSource.LoadDataListener loadDataListener;
	private int placeholderId;
	private int errorId;
	private ModelType model;
	protected Class<ModelType> modelClass;
	private LocalDataSource.MemoryCacheStrategy memoryCacheStrategy;
	private LocalDataSource.DiskCacheStrategy diskCacheStrategy;
	private Context context;
	private ViewTarget viewTarget;
	private ResourceTransformer resourceTransformer;

	public GenericRequest(RequestOrder<ModelType> requestTaskOrder, DataSource.LoadDataListener loadDataListener, int placeholderId, int errorId,
						  ModelType model, Class<ModelType> modelClass, LocalDataSource.MemoryCacheStrategy memoryCacheStrategy, LocalDataSource
								  .DiskCacheStrategy diskCacheStrategy, Context context,  ViewTarget
								  viewTarget, ResourceTransformer resourceTransformer) {
		this.requestTaskOrder = requestTaskOrder;
		this.loadDataListener = loadDataListener;
		this.placeholderId = placeholderId;
		this.errorId = errorId;
		this.model = model;
		this.modelClass = modelClass;
		this.memoryCacheStrategy = memoryCacheStrategy;
		this.diskCacheStrategy = diskCacheStrategy;
		this.context = context;
		this.viewTarget = viewTarget;
		this.resourceTransformer = resourceTransformer;
	}

	public static <ModelType> GenericRequest<ModelType> obtain(RequestOrder<ModelType> requestTaskOrder, DataSource.LoadDataListener
			loadDataListener, int placeholderId, int errorId, ModelType model, Class<ModelType> modelClass, LocalDataSource.MemoryCacheStrategy
			memoryCacheStrategy, LocalDataSource.DiskCacheStrategy diskCacheStrategy, Context context, ViewTarget viewTarget, ResourceTransformer
			resourceTransformer

	) {
		return new GenericRequest<>(requestTaskOrder, loadDataListener, placeholderId, errorId, model, modelClass, memoryCacheStrategy,
				diskCacheStrategy, context, viewTarget, resourceTransformer);
	}

	public RequestOrder<ModelType> getRequestTaskOrder() {
		return requestTaskOrder;
	}

	public DataSource.LoadDataListener getRequestListener() {
		return loadDataListener;
	}

	public int getPlaceholderId() {
		return placeholderId;
	}

	public int getErrorId() {
		return errorId;
	}

	public ModelType getModel() {
		return model;
	}

	public Class<ModelType> getModelClass() {
		return modelClass;
	}

	public LocalDataSource.MemoryCacheStrategy getMemoryCacheStrategy() {
		return memoryCacheStrategy;
	}

	public LocalDataSource.DiskCacheStrategy getDiskCacheStrategy() {
		return diskCacheStrategy;
	}

	public Context getContext() {
		return context;
	}


	public ViewTarget getViewTarget() {
		return viewTarget;
	}

	public ResourceTransformer getResourceTransformer() {
		return resourceTransformer;
	}
}
