package com.joy.glide.library.request.target;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by joybar on 2018/6/4.
 */

public class DrawableImageViewTarget extends ImageViewTarget<Drawable> {
	public DrawableImageViewTarget(ImageView view) {
		super(view);
	}

	@Override
	protected void setResource(Drawable resource) {
		view.setImageDrawable(resource);
	}
}
