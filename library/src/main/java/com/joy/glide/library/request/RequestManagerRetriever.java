package com.joy.glide.library.request;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.joy.glide.library.drawable.DrawableRequest;
import com.joy.glide.library.helper.lifecycle.LifecycleHelper;
import com.joy.glide.library.request.target.RequestListener;
import com.joy.glide.library.utils.GLog;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

/**
 * Created by joybar on 11/05/2018.
 */

public class RequestManagerRetriever {

	public static RequestManager get(Context context) {
		final RequestManager requestManager = new RequestManager();
		createLifeMonitor(context, requestManager);
		return requestManager;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static RequestManager get(android.app.Fragment fragment) {
		final RequestManager requestManager = new RequestManager();
		createLifeMonitor(fragment, requestManager);
		return requestManager;
	}

	public static RequestManager get(android.support.v4.app.Fragment fragment) {
		final RequestManager requestManager = new RequestManager();
		createLifeMonitor(fragment, requestManager);
		return requestManager;
	}


	private static void createLifeMonitor(Context context, final RequestManager requestManager) {
		LifecycleHelper.newInstance().registerLifecycleListener(context, new LifecycleListener() {
			@Override
			public void onStart() {
				GLog.printInfo("onStart");
			}

			@Override
			public void onResume() {
				GLog.printInfo("onResume");
			}

			@Override
			public void onPause() {
				GLog.printInfo("onPause");
			}

			@Override
			public void onStop() {
				GLog.printInfo("onStop");
			}

			@Override
			public void onDestroy() {
				DrawableRequest drawableRequestBuilder = requestManager.getRequestBuilder();
				if (null != drawableRequestBuilder && !drawableRequestBuilder.isLoadReady()) {
					RequestListener requestListener = drawableRequestBuilder.getRequestListener();
					GLog.printInfo("onDestroy,so cancelRequest");
					if (null != requestListener) {
						requestListener.onCancelled();
					}
					drawableRequestBuilder.cancelRequest();
				}
			}
		});

	}


	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	private static void createLifeMonitor(android.app.Fragment fragment, final RequestManager requestManager) {

		LifecycleHelper.newInstance().registerLifecycleListener(fragment, "android.app.Fragment", new LifecycleListener() {
			@Override
			public void onStart() {

			}

			@Override
			public void onResume() {

			}

			@Override
			public void onPause() {

			}

			@Override
			public void onStop() {

			}

			@Override
			public void onDestroy() {
				DrawableRequest drawableRequestBuilder = requestManager.getRequestBuilder();
				if (null != drawableRequestBuilder && !drawableRequestBuilder.isLoadReady()) {
					RequestListener requestListener = drawableRequestBuilder.getRequestListener();
					GLog.printInfo("onDestroy,so cancelRequest");
					if (null != requestListener) {
						requestListener.onCancelled();
					}
					drawableRequestBuilder.cancelRequest();
				}
			}
		});

	}

	private static void createLifeMonitor(android.support.v4.app.Fragment fragment, final RequestManager requestManager) {

		LifecycleHelper.newInstance().registerLifecycleListener(fragment, "android.support.v4.app" + ".Fragment", new LifecycleListener() {
			@Override
			public void onStart() {

			}

			@Override
			public void onResume() {

			}

			@Override
			public void onPause() {

			}

			@Override
			public void onStop() {

			}

			@Override
			public void onDestroy() {
				DrawableRequest drawableRequestBuilder = requestManager.getRequestBuilder();
				if (null != drawableRequestBuilder && !drawableRequestBuilder.isLoadReady()) {
					RequestListener requestListener = drawableRequestBuilder.getRequestListener();
					GLog.printInfo("onDestroy,so cancelRequest");
					if (null != requestListener) {
						requestListener.onCancelled();
					}
					drawableRequestBuilder.cancelRequest();
				}
			}
		});

	}


}
