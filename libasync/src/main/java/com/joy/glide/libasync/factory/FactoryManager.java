package com.joy.glide.libasync.factory;


import com.joy.glide.libasync.task.ProductLineDispatcher2;
import com.joy.glide.libasync.task.TaskRunnable;

/**
 * Created by joybar on 2018/5/11.
 */

public class FactoryManager {
	public static String TAG = "FactoryManager";

	public FactoryManager() {

	}

	public static FactoryManager getInstance() {
		return FactoryManagerHolder.INSTANCE;
	}

	private static class FactoryManagerHolder {
		private static FactoryManager INSTANCE = new FactoryManager();
	}

	public void produce(TaskRunnable taskRunnable) {
		ProductLineDispatcher2.getInstance().execute(taskRunnable);
	}



}
