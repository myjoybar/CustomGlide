package com.joy.glide.library.drawable;

import android.content.Context;
import android.widget.ImageView;

import com.joy.glide.library.data.DataSource;

/**
 * Created by joybar on 2018/5/9.
 */

public class DrawableRequest<ModelType> extends DrawableRequestBuilder<ModelType> {


	public DrawableRequest(Context context, Class<ModelType> modelClass, ModelType url) {
		super(context, modelClass, url);
	}

	@Override
	public void into(ImageView imageView) {
		super.into(imageView);
	}

	public DrawableRequest placeholder(int resourceId) {
		super.placeholder(resourceId);
		return this;
	}

	@Override
	public DrawableRequest error(int resourceId) {
		super.error(resourceId);
		return this;
	}

	@Override
	public DrawableRequest listener(DataSource.LoadDataListener loadDataListener) {
		super.listener(loadDataListener);
		return this;
	}


}
