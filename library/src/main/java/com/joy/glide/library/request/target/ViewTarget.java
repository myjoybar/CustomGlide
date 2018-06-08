package com.joy.glide.library.request.target;

import android.view.View;

import com.joy.glide.library.utils.CheckUtils;

/**
 * Created by joybar on 2018/6/4.
 */

public abstract class ViewTarget<T extends View, Z> extends BaseTarget<Z> {

	protected final T view;

	public ViewTarget(T view) {
		CheckUtils.checkNotNull(view, "View must not be null!");
		this.view = view;
	}

	/**
	 * Returns the wrapped {@link android.view.View}.
	 */
	public T getView() {
		return view;
	}


	public void setPlaceHolder(int rid) {

	}

	public void setErrorHolder(int rid) {

	}

	public int getWidth() {
		return view.getWidth();
	}

	public int getHeight() {

		return view.getHeight();
	}

}
