package com.joy.glide.library.drawable;

import com.joy.glide.library.load.engine.DiskCacheStrategy;

/**
 * Created by joybar on 2018/5/24.
 */

public class DrawableRequestBuilder<ModelType> extends GenericRequestBuilder<ModelType> {


	public DrawableRequestBuilder(Class<ModelType> modelClass,ModelType model) {
		super(modelClass, model);
	}

	public DrawableRequestBuilder<ModelType> load(ModelType model) {
		super.load(model);
		return this;
	}

	@Override
	public DrawableRequestBuilder<ModelType> diskCacheStrategy(DiskCacheStrategy strategy) {
		super.diskCacheStrategy(strategy);
		return this;
	}



}
