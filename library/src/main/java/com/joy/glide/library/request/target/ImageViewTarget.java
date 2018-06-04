package com.joy.glide.library.request.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.joy.glide.library.request.RequestOrder;
import com.joy.glide.library.request.adapter.ViewAdapter;

/**
 * Created by joybar on 2018/6/4.
 */

public abstract class ImageViewTarget<Z> extends ViewTarget<ImageView, Z>  implements ViewAdapter {

	public ImageViewTarget(ImageView view) {
		super(view);
	}

	@Override
	public Drawable getCurrentDrawable() {
		return null;
	}

	@Override
	public void setDrawable(Drawable drawable) {

	}

	@Override
	public void onDataLoaded(Z z) {
		super.onDataLoaded(z);
		setResource(z);
	}

	@Override
	public void onDataLoadedError(@NonNull Throwable throwable) {
		super.onDataLoadedError(throwable);
	}

	@Override
	public void onProgressUpdate(int value) {
		super.onProgressUpdate(value);
	}

	protected abstract void setResource(Z resource);

	@Override
	public void setErrorHolder(int rid) {
		super.setErrorHolder(rid);
		view.setImageResource(rid);
	}

	@Override
	public void setPlaceHolder(int rid) {
		super.setPlaceHolder(rid);
		view.setImageResource(rid);
	}

	@Override
	public RequestOrder getRequestOrder() {
		return super.getRequestOrder();
	}

}
