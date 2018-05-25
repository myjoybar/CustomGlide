package com.joy.glide.library.request;

import com.joy.glide.library.drawable.DrawableRequest;

import java.io.File;

/**
 * Created by joybar on 2018/5/8.
 */

public class RequestManager {

	DrawableRequest drawableRequestBuilder;

	public RequestManager() {
	}

	public DrawableRequest getRequestBuilder() {
		return drawableRequestBuilder;
	}

	public DrawableRequest<String> load(String string) {
		drawableRequestBuilder = new DrawableRequest(string);
		return drawableRequestBuilder;
	}

	public DrawableRequest<File> load(File file) {
		//return (DrawableTypeRequest<File>) fromFile().load(file);
		return null;
	}

}
