package com.joy.glide.library.request;

import android.widget.ImageView;

import com.joy.glide.library.ctasynctask.netexecutor.AbstractExecutor;
import com.joy.glide.library.engine.EngineManager;
import com.joy.glide.library.engine.LoaderEngine;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

/**
 * Created by joybar on 2018/5/9.
 */

public class RequestOrder {

	LoaderEngine engine;
	LifecycleListener lifecycleListener;

	public RequestOrder(String url) {
		engine = EngineManager.getInstance().prepareEngine(url, createLifecycleListener());
	}

	LifecycleListener createLifecycleListener(){
		if(null == lifecycleListener){
			lifecycleListener = new LifecycleListener() {
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

				}
			};
		}
		return lifecycleListener;
	}

	public void submit(){

	}
	public void into(final ImageView imageView){
		final AbstractExecutor abLoader = engine.getAbLoader();
		new Thread(){
			@Override
			public void run() {
				super.run();
//				final Bitmap bitmap = abLoader.loadBitmap(null);
//				imageView.post(new Runnable() {
//					@Override
//					public void run() {
//						if (imageView != null && bitmap != null) {
//							imageView.setImageBitmap(bitmap);
//						}
//					}
//				});

			}
		}.start();
	}
}
