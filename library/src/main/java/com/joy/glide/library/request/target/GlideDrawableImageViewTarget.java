package com.joy.glide.library.request.target;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by joybar on 2018/6/5.
 */

public class GlideDrawableImageViewTarget extends ImageViewTarget<Drawable> {
	public GlideDrawableImageViewTarget(ImageView view) {
		super(view);
	}

	@Override
	protected void setResource(Drawable drawable) {
		view.setImageDrawable(drawable);
	}

}