package com.joy.glide.library.resource;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by joybar on 2018/6/4.
 */

public class BitmapTransformer implements ResourceTransformer<Drawable,Bitmap> {
	@Override
	public Bitmap transform(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
		Bitmap bitmap = bitmapDrawable.getBitmap();
		return bitmap;
	}
}
