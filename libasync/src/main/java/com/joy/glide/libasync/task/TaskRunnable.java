package com.joy.glide.libasync.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.joy.glide.libasync.util.Util;


/**
 * Created by joybar on 2018/5/10.
 */

public abstract class TaskRunnable<TProgress, TResult> implements Runnable {


	private static final int MESSAGE_POST_RESULT = 0x1;
	private static final int MESSAGE_POST_PROGRESS = 0x2;

	protected String name;
	private static InternalHandler sHandler;

	public TaskRunnable(String format, Object... args) {
		this.name = Util.format(format, args);

	}

	public TaskRunnable() {

	}

	@Override
	public void run() {
		String oldName = Thread.currentThread().getName();
		TResult result = null;
		if (!TextUtils.isEmpty(name)) {
			Thread.currentThread().setName(name);
		}
		try {
			result = doInBackground();
		} finally {
			Thread.currentThread().setName(oldName);
		}
		postResult(result);
	}

	protected void onPreExecute() {

	}

	protected abstract TResult doInBackground();

	protected void onProgressUpdate(TProgress... values) {

	}

	private void postResult(TResult result) {
		@SuppressWarnings("unchecked") Message message = getMainHandler().obtainMessage(MESSAGE_POST_RESULT, new TaskCallableResult<TResult>(this,
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


	private static Handler getMainHandler() {
		synchronized (AsyncTask.class) {
			if (sHandler == null) {
				sHandler = new InternalHandler(Looper.getMainLooper());
			}
			return sHandler;
		}
	}

	@SuppressWarnings({"RawUseOfParameterizedType"})
	private static class TaskCallableResult<Data> {
		final TaskRunnable mTask;
		final Data[] mData;

		TaskCallableResult(TaskRunnable task, Data... data) {
			mTask = task;
			mData = data;
		}
	}

}
