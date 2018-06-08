package com.joy.glide.library.request;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.drawable.DrawableRequest;
import com.joy.glide.library.helper.lifecycle.LifecycleHelper;
import com.joy.glide.library.utils.GLog;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joybar on 11/05/2018.
 */

public class RequestManagerRetriever {

	private static List<RequestManager> requestManagerList = new ArrayList<>();
	private static Map<Context, List<RequestManager>> requestManagerContextMap = new HashMap<>();
	private static Map<android.app.Fragment, List<RequestManager>> requestManagerFragmentMap = new HashMap<>();
	private static Map<android.support.v4.app.Fragment, List<RequestManager>> requestManagerV4FragmentMap = new HashMap<>();

	public static RequestManager get(Context context) {
		final RequestManager requestManager = new RequestManager(context);
		createLifeMonitor(context, requestManager);
		return requestManager;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static RequestManager get(android.app.Fragment fragment) {
		final RequestManager requestManager = new RequestManager(fragment.getContext());
		createLifeMonitor(fragment, requestManager);
		return requestManager;
	}

	public static RequestManager get(android.support.v4.app.Fragment fragment) {
		final RequestManager requestManager = new RequestManager(fragment.getContext());
		createLifeMonitor(fragment, requestManager);
		return requestManager;
	}


	private static void createLifeMonitor(final Context context, final RequestManager requestManager) {
		if (requestManagerContextMap.containsKey(context)) {
			List<RequestManager> requestManagerList = requestManagerContextMap.get(context);
			requestManagerList.add(requestManager);
		} else {
			List<RequestManager> requestManagerList = new ArrayList<>();
			requestManagerList.add(requestManager);
			requestManagerContextMap.put(context, requestManagerList);
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
					if (requestManagerContextMap.containsKey(context)) {
						List<RequestManager> requestManagerList = requestManagerContextMap.get(context);
						int count = requestManagerList.size();
						for (int i = 0; i < count; i++) {
							RequestManager rManager =requestManagerList.get(i);
							DrawableRequest drawableRequestBuilder = rManager.getRequestBuilder();
							if (null != drawableRequestBuilder) {
								GLog.printInfo("onDestroy");
								DataSource.LoadDataListener loadDataListener = drawableRequestBuilder.getLoadDataListener();
								if (null != loadDataListener) {
									GLog.printInfo("onDestroy,so cancelRequest");
									loadDataListener.onCancelled();
								}
								drawableRequestBuilder.cancelRequest();
							}
						}
					}

				}
			});
		}
	}


	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	private static void createLifeMonitor(final android.app.Fragment fragment, final RequestManager requestManager) {
		if (requestManagerFragmentMap.containsKey(fragment)) {
			List<RequestManager> requestManagerList = requestManagerFragmentMap.get(fragment);
			requestManagerList.add(requestManager);
		} else {
			List<RequestManager> requestManagerList = new ArrayList<>();
			requestManagerList.add(requestManager);
			requestManagerFragmentMap.put(fragment, requestManagerList);

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
					if (requestManagerFragmentMap.containsKey(fragment)) {
						List<RequestManager> requestManagerList = requestManagerFragmentMap.get(fragment);
						int count = requestManagerList.size();
						for (int i = 0; i < count; i++) {
							RequestManager rManager =requestManagerList.get(i);
							DrawableRequest drawableRequestBuilder = rManager.getRequestBuilder();
							if (null != drawableRequestBuilder) {
								GLog.printInfo("onDestroy");
								DataSource.LoadDataListener loadDataListener = drawableRequestBuilder.getLoadDataListener();
								if (null != loadDataListener) {
									GLog.printInfo("onDestroy,so cancelRequest");
									loadDataListener.onCancelled();
								}
								drawableRequestBuilder.cancelRequest();
							}
						}
					}
				}
			});

		}


	}

	private static void createLifeMonitor(final android.support.v4.app.Fragment fragment, final RequestManager requestManager) {

		if (requestManagerV4FragmentMap.containsKey(fragment)) {
			List<RequestManager> requestManagerList = requestManagerV4FragmentMap.get(fragment);
			requestManagerList.add(requestManager);
		} else {
			List<RequestManager> requestManagerList = new ArrayList<>();
			requestManagerList.add(requestManager);
			requestManagerV4FragmentMap.put(fragment, requestManagerList);
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
					if (requestManagerV4FragmentMap.containsKey(fragment)) {
						List<RequestManager> requestManagerList = requestManagerV4FragmentMap.get(fragment);
						int count = requestManagerList.size();
						for (int i = 0; i < count; i++) {
							RequestManager rManager =requestManagerList.get(i);
							DrawableRequest drawableRequestBuilder = rManager.getRequestBuilder();
							if (null != drawableRequestBuilder) {
								GLog.printInfo("onDestroy");
								DataSource.LoadDataListener loadDataListener = drawableRequestBuilder.getLoadDataListener();
								if (null != loadDataListener) {
									GLog.printInfo("onDestroy,so cancelRequest");
									loadDataListener.onCancelled();
								}
								drawableRequestBuilder.cancelRequest();
							}
						}
					}
				}
			});
		}


	}


}
