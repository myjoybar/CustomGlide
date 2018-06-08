package com.joy.glide.library;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.joy.glide.library.load.engine.bitmap_recycle.BitmapPool;
import com.joy.glide.library.request.RequestManager;
import com.joy.glide.library.request.RequestManagerRetriever;

/**
 * Created by joybar on 2018/5/8.
 */

public class Glide {

	private static final String TAG = "Glide";
	private static volatile Glide glide;
	private final BitmapPool bitmapPool;

	public Glide(BitmapPool bitmapPool) {
		this.bitmapPool = bitmapPool;
	}

	public static RequestManager with(Context context) {
		return RequestManagerRetriever.get(context);
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static RequestManager with(android.app.Fragment fragment) {
		return RequestManagerRetriever.get(fragment);
	}

	public static RequestManager with(android.support.v4.app.Fragment fragment) {
		return RequestManagerRetriever.get(fragment);
	}



	public static Glide get(Context context) {
		if (glide == null) {
			synchronized (Glide.class) {
				if (glide == null) {
					Context applicationContext = context.getApplicationContext();
					GlideBuilder builder = new GlideBuilder(applicationContext);
					glide = builder.createGlide();

				}
			}
		}

		return glide;
	}

	public BitmapPool getBitmapPool() {
		return bitmapPool;
	}
}
