package com.joy.glide.library.request.target;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by joybar on 2018/6/4.
 */

public class BitmapImageViewTarget extends ImageViewTarget<Bitmap> {
	public BitmapImageViewTarget(ImageView view) {
		super(view);
	}

	@Override
	protected void setResource(Bitmap resource) {
		view.setImageBitmap(resource);
	}

}



