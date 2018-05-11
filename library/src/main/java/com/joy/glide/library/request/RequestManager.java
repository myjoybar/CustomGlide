package com.joy.glide.library.request;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.joy.glide.library.Glide;
import com.joy.glide.library.lifecycle.LifecycleHelper;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

/**
 * Created by joybar on 2018/5/8.
 */

public class RequestManager {

	private Context context;
	private Glide glide;
	LifecycleListener lifecycleListener;

	public RequestManager() {
	}

	public RequestOrder load(String url) {

		return new RequestOrder(url);
	}

	public RequestManager(Context context, LifecycleListener lifecycleListener, Glide glide) {
		this.context = context;
		this.glide = glide;
	}


//	public RequestManager get(Context context) {
//
//	}

	public void registerLifeCycle(Context context) {
		LifecycleHelper.newInstance().registerLifecycleListener(context, lifecycleListener);
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	public void registerLifeCycle(android.app.Fragment fragment) {
		LifecycleHelper.newInstance().registerLifecycleListener(fragment, "android.app.Fragment", lifecycleListener);
	}

	public void registerLifeCycle(android.support.v4.app.Fragment fragment) {
		LifecycleHelper.newInstance().registerLifecycleListener(fragment, "v4.app.Fragment", lifecycleListener);
	}
}
