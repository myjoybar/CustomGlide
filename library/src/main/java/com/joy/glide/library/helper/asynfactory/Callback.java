package com.joy.glide.library.helper.asynfactory;


import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 2018/5/9.
 */

public interface Callback<TResult> {
    void onPreExecute(AsynchronousTask asynchronousTask);

    void onFailure();

    void onResponse(TResult result);
}
