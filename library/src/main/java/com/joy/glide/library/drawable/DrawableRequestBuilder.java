package com.joy.glide.library.drawable;

import android.content.Context;

import com.joy.glide.library.data.source.local.LocalDataSource;
import com.joy.glide.library.load.engine.DiskCacheStrategy;

/**
 * Created by joybar on 2018/5/24.
 */

public class DrawableRequestBuilder<ModelType> extends GenericRequestBuilder<ModelType> {


	public DrawableRequestBuilder(Context context, Class<ModelType> modelClass, ModelType url) {
		super(context, modelClass, url);
	}

	@Override
	public DrawableRequestBuilder<ModelType> load(ModelType model) {
		super.load(model);
		return this;
	}

	@Override
	public DrawableRequestBuilder<ModelType> diskCacheStrategy(DiskCacheStrategy strategy) {
		super.diskCacheStrategy(strategy);
		return this;
	}
	public GenericRequestBuilder<ModelType> cacheStrategySwitcher(LocalDataSource.CacheStrategySwitcher cacheStrategySwitcher) {
		super.cacheStrategySwitcher(cacheStrategySwitcher);
		return this;
	}




}
