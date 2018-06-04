package com.joy.glide.library.request.target;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.joy.glide.library.request.adapter.ViewAdapter;

/**
 * Created by joybar on 2018/6/4.
 */

public class SimpleDrawableViewTarget extends ViewTarget<View, Drawable>  implements ViewAdapter {

	public SimpleDrawableViewTarget(View view) {
		super(view);
	}

	@Override
	public Drawable getCurrentDrawable() {
		return null;
	}
	@Override
	public void onDataLoaded(Drawable drawable) {
		super.onDataLoaded(drawable);
		setDrawable(drawable);
	}
	@Override
	public void setDrawable(Drawable drawable) {
		view.setBackground(drawable);
	}

	@Override
	public void setPlaceHolder(int rid) {
		super.setPlaceHolder(rid);
		view.setBackgroundResource(rid);
	}

	@Override
	public void setErrorHolder(int rid) {
		super.setErrorHolder(rid);
		view.setBackgroundResource(rid);
	}
}
