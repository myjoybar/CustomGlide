package com.joy.glide.library.asynfactory;


import com.joy.glide.libasync.factory.Callback;
import com.joy.glide.libasync.order.RequestTaskOrder;
import com.joy.glide.libasync.task.ProductLineDispatcher2;
import com.joy.glide.libasync.task.TaskRunnable;

/**
 * Created by joybar on 2018/5/11.
 */

public class AsyncFactory<TResult> {
	public static String TAG = "FactoryManager";

	public AsyncFactory() {

	}

	public static AsyncFactory getInstance() {
		return FactoryManagerHolder.INSTANCE;
	}

	private static class FactoryManagerHolder {
		private static AsyncFactory INSTANCE = new AsyncFactory();
	}

	public void addOrder(RequestTaskOrder taskOrder) {

	}


	public void produce(RequestTaskOrder taskOrder) {
		produce(taskOrder, null);
	}

	public void produce(RequestTaskOrder taskOrder, Callback callback) {
		TaskRunnable taskRunnable = createTaskRunnable(taskOrder, callback);
		ProductLineDispatcher2.getInstance().execute(taskRunnable);
	}

	private TaskRunnable createTaskRunnable(RequestTaskOrder taskOrder, final Callback<TResult> callback) {
		TaskRunnable taskRunnable = new TaskRunnable<Integer, TResult>() {

			@Override
			protected TResult doInBackground() {
				TResult result = (TResult) "AAAA";
				return result;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				super.onProgressUpdate(values);
			}

			@Override
			protected void onPostExecute(TResult result) {
				super.onPostExecute(result);
				if (null != callback) {
					callback.onResponse(result);
				}
			}
		};
		return taskRunnable;
	}


}
