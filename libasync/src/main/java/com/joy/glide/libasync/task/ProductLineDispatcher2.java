package com.joy.glide.libasync.task;

import com.joy.glide.libasync.pool.ProductLineThreadPool;

/**
 * Created by joybar on 2018/5/11.
 */

public class ProductLineDispatcher2 {

	public ProductLineDispatcher2() {

	}

	public static ProductLineDispatcher2 getInstance() {
		return ProductLineDispatcherHolder.INSTANCE;
	}

	private static class ProductLineDispatcherHolder {
		private static ProductLineDispatcher2 INSTANCE = new ProductLineDispatcher2();
	}


	public synchronized void execute(Runnable runnable) {
		ProductLineThreadPool.getExecutorService().execute(runnable);

	}
}
