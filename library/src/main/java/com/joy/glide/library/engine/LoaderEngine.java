package com.joy.glide.library.engine;

import com.joy.glide.library.ctasynctask.TaskOrder;
import com.joy.glide.library.ctasynctask.netexecutor.AbstractExecutor;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

/**
 * Created by joybar on 2018/5/9.
 */

public class LoaderEngine<Result> {
	private final TaskOrder taskOrder;
	private final AbstractExecutor<Result> abLoader;
	private final LifecycleListener lifecycleListener;

	public LoaderEngine(TaskOrder taskOrder, AbstractExecutor abLoader, LifecycleListener lifecycleListener) {
		this.taskOrder = taskOrder;
		this.abLoader = abLoader;
		this.lifecycleListener = lifecycleListener;
	}

	public AbstractExecutor getAbLoader() {
		return abLoader;
	}

	public LifecycleListener getLifecycleListener() {
		return lifecycleListener;
	}

	public TaskOrder getTaskOrder() {
		return taskOrder;
	}
}

