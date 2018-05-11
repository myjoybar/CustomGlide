package com.joy.glide.library.ctasynctask.netexecutor;


import com.joy.glide.library.ctasynctask.HttpUrlConnectionExecutor;
import com.joy.glide.library.ctasynctask.TaskOrder;

/**
 * Created by joybar on 2018/5/9.
 */

public class ExecutorFactory {
	public ExecutorFactory() {
	}

	public static ExecutorFactory getInstance() {
		return LoaderFactoryHolder.INSTANCE;
	}

	private static class LoaderFactoryHolder {
		private static ExecutorFactory INSTANCE = new ExecutorFactory();
	}

	private AbstractExecutor abstractLoader;

	public void register(AbstractExecutor abstractLoader) {
		this.abstractLoader = abstractLoader;
	}

	public AbstractExecutor get(TaskOrder taskOrder) {

		if (null == this.abstractLoader) {
			return getDefaultLoader(taskOrder);
		}
		return abstractLoader;
	}

	private AbstractExecutor getDefaultLoader(TaskOrder taskOrder) {
		AbstractExecutor abstractLoader = new HttpUrlConnectionExecutor(taskOrder);
		return abstractLoader;
	}

}
