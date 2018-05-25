package com.joy.glide.library.drawable;

import android.widget.ImageView;

import com.joy.glide.library.request.target.RequestListener;

/**
 * Created by joybar on 2018/5/9.
 */

public class DrawableRequest<ModelType> extends DrawableRequestBuilder<ModelType> {


	public DrawableRequest(String url) {
		super(url);
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
	public DrawableRequest listener(RequestListener requestListener) {
		super.listener(requestListener);
		return this;
	}



}
