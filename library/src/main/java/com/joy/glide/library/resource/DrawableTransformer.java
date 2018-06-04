package com.joy.glide.library.resource;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by joybar on 2018/6/4.
 */

public class DrawableTransformer implements ResourceTransformer<Bitmap,Drawable> {
	@Override
	public Drawable transform(Bitmap bitmap) {
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		return bitmapDrawable;
	}
}
