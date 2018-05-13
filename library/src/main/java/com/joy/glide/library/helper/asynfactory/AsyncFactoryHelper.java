package com.joy.glide.library.helper.asynfactory;


import com.joy.glide.library.http.HttpFactorySelector;
import com.joy.glide.library.request.RequestOrder;

import me.joy.async.lib.factory.AsyncFactory;
import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 2018/5/11.
 */

public class AsyncFactoryHelper<TResult> {
    public static String TAG = "AsyncFactoryHelper";

    public AsyncFactoryHelper() {

    }

    public static AsyncFactoryHelper getInstance() {
        return FactoryManagerHolder.INSTANCE;
    }

    private static class FactoryManagerHolder {
        private static AsyncFactoryHelper INSTANCE = new AsyncFactoryHelper();
    }


    public void produce(RequestOrder taskOrder, Callback callback) {
        AsynchronousTask asynchronousTask = createTaskRunnable(taskOrder, callback);
        AsyncFactory.getInstance().produce(asynchronousTask);
    }

    private AsynchronousTask createTaskRunnable(final RequestOrder taskOrder, final
    Callback<TResult> callback) {
        AsynchronousTask asynchronousTask = new AsynchronousTask<Integer, TResult>() {

            @Override
            public void onPreExecute() {
                super.onPreExecute();
                if (null != callback) {
                    callback.onPreExecute(this);
                }
            }

            @Override
            protected TResult doInBackground() {
                TResult result = (TResult) HttpFactorySelector.getInstance().get(new RequestOrder
                        (taskOrder.getUrl())).execute();
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
                    if (null != result) {
                        callback.onResponse(result);
                    } else {
                        callback.onFailure();
                    }
                }
            }
        };
        return asynchronousTask;
    }


}
