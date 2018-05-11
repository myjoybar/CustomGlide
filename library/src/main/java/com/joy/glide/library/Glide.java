package com.joy.glide.library;

import android.support.v4.app.FragmentActivity;

import com.joy.glide.library.request.RequestManager;

/**
 * Created by joybar on 2018/5/8.
 */

public class Glide {

	public static RequestManager with(FragmentActivity activity) {
//		RequestManager requestManager = RequestManagerRetriever.get();
//		return retriever.get(activity);

		return  new RequestManager();
	}

}
