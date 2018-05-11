package com.joy.glide.libasync.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.joy.glide.libasync.util.Util;

import java.util.concurrent.Callable;

/**
 * Created by joybar on 2018/5/10.
 */

public abstract class TaskCallable<TParams, TProgress,TResult> implements Callable {

	private static final int MESSAGE_POST_RESULT = 0x1;
	private static final int MESSAGE_POST_PROGRESS = 0x2;

	protected final String name;
	private final Handler mHandler;

	public TaskCallable(String format, Object... args) {
		mHandler = new Handler(Looper.getMainLooper());
		this.name = Util.format(format, args);

	}

	@Override
	public TResult call() throws Exception {
		String oldName = Thread.currentThread().getName();
		TResult result = null;
		Thread.currentThread().setName(name);
		try {
			result = doInBackground();
		} finally {
			Thread.currentThread().setName(oldName);
		}
		postResult(result);
		return result;
	}

	protected void onPreExecute() {

	}

	protected abstract TResult doInBackground(TParams... params);

	protected void onProgressUpdate(TParams... values) {

	}

	private void postResult(TResult result) {
		@SuppressWarnings("unchecked")
		Message message = getHandler().obtainMessage(MESSAGE_POST_RESULT, new TaskCallableResult<TResult>(this,
				result));
		message.sendToTarget();
	}

	protected void onPostExecute(TResult result) {

	}

	private static class InternalHandler extends Handler {
		public InternalHandler(Looper looper) {
			super(looper);
		}

		@SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
		@Override
		public void handleMessage(Message msg) {
			TaskCallableResult result = (TaskCallableResult) msg.obj;
			switch (msg.what) {
				case MESSAGE_POST_RESULT:
					result.mTask.finish(result.mData[0]);
					break;
				case MESSAGE_POST_PROGRESS:
					result.mTask.onProgressUpdate(result.mData);
					break;
			}
		}
	}

	private void finish(TResult result) {
		onPostExecute(result);
	}

	private Handler getHandler() {
		return mHandler;
	}

	@SuppressWarnings({"RawUseOfParameterizedType"})
	private static class TaskCallableResult<Data> {
		final TaskCallable mTask;
		final Data[] mData;

		TaskCallableResult(TaskCallable task, Data... data) {
			mTask = task;
			mData = data;
		}
	}

}
