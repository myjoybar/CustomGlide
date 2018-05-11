package com.joy.glide.library.ctasynctask;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by joybar on 2018/5/9.
 */

public class Dispatcher {
	private int maxRequests = 64;
	private ExecutorService executorService;
	/**
	 * Running asynchronous calls. Includes canceled calls that haven't finished yet.
	 */
	private final Deque<AsyncTaskRunnable> runningAsyncCalls = new ArrayDeque<>();

	public synchronized ExecutorService executorService() {
		if (executorService == null) {
			executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util
					.threadFactory("AsyncTaskClient Dispatcher", false));
		}
		return executorService;
	}

	public static ThreadFactory threadFactory(final String name, final boolean daemon) {
		return new ThreadFactory() {
			@Override
			public Thread newThread(Runnable runnable) {
				Thread result = new Thread(runnable, name);
				result.setDaemon(daemon);
				return result;
			}
		};
	}

	synchronized void execute(AsyncTaskRunnable asyncTaskRunnable) {
		if (runningAsyncCalls.size() < maxRequests) {
			runningAsyncCalls.add(asyncTaskRunnable);
			executorService().execute(asyncTaskRunnable);
		} else {
			runningAsyncCalls.add(asyncTaskRunnable);
		}
	}

}
