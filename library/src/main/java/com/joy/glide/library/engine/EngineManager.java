package com.joy.glide.library.engine;

import com.joy.glide.library.ctasynctask.TaskOrder;
import com.joy.glide.library.ctasynctask.netexecutor.AbstractExecutor;
import com.joy.glide.library.ctasynctask.netexecutor.ExecutorFactory;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

/**
 * Created by joybar on 2018/5/8.
 */

public class EngineManager {

	public EngineManager() {
	}

	public static EngineManager getInstance() {
		return EngineManagerHolder.INSTANCE;
	}

	private static class EngineManagerHolder {
		private static EngineManager INSTANCE = new EngineManager();
	}

	public LoaderEngine prepareEngine(String url, LifecycleListener lifecycleListener) {
		TaskOrder taskOrder = new TaskOrder(url);
		AbstractExecutor abLoader = ExecutorFactory.getInstance().get(taskOrder);
		LoaderEngine loaderEngine = new LoaderEngine(taskOrder,abLoader, lifecycleListener);
		return loaderEngine;
	}

}
