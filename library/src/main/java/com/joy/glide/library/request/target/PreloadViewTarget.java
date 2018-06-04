package com.joy.glide.library.request.target;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.joy.glide.library.request.adapter.ViewAdapter;

/**
 * Created by joybar on 2018/6/4.
 */

public  class PreloadViewTarget<T extends View, Z> extends ViewTarget<T, Z> implements ViewAdapter {


	public PreloadViewTarget(T emptyView) {
		super(emptyView);
	}

	@Override
	public Drawable getCurrentDrawable() {
		return null;
	}

	@Override
	public void setDrawable(Drawable drawable) {

	}
}
