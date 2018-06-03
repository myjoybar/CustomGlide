package com.joy.glide.library.request;

import android.content.Context;

import com.joy.glide.library.drawable.DrawableRequest;

import java.io.File;

/**
 * Created by joybar on 2018/5/8.
 */

public class RequestManager {

	DrawableRequest drawableRequestBuilder;
	Context context;

	public RequestManager(Context context) {
		this.context = context;
	}

	public DrawableRequest getRequestBuilder() {
		return drawableRequestBuilder;
	}

	public DrawableRequest<String> load(String string) {
		return drawableRequestBuilder = new DrawableRequest<String>(context, String.class, string);
	}

	public DrawableRequest<File> load(File file) {
		return drawableRequestBuilder = new DrawableRequest<File>(context, File.class, file);
	}

	public <T> DrawableRequest<T> load(T model) {
		return drawableRequestBuilder = new DrawableRequest<T>(context, getSafeClass(model), model);
	}

	private static <T> Class<T> getSafeClass(T model) {
		return model != null ? (Class<T>) model.getClass() : null;
	}

}
