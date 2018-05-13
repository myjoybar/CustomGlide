package com.joy.glide.library.request;

/**
 * Created by joybar on 2018/5/8.
 */

public class RequestManager {

	RequestHolder holder;

	public RequestManager() {
	}

	public RequestHolder getHolder() {
		return holder;
	}

	public RequestHolder load(String url) {
		holder = new RequestHolder(url);
		return holder;
	}


}
